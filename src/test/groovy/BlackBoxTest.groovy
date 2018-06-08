import com.marshmallow.demo.DemoApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Shared
import spock.lang.Specification

@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration
class BlackBoxSpec extends Specification {

    int port = 8080

    @Shared
    OilSpillClient client

    def setupSpec() {
        client = new OilSpillClient()
    }

    def setup() {
        client.setupClient(port)
    }


    def "correct example response"() {
        given: "valid input"

        def input = [
            "areaSize" : [5, 5],
            "startingPosition" : [1, 2],
            "oilPatches" : [
                    [1, 0],
                    [2, 2],
                    [2, 3]
            ],
            "navigationInstructions" : "NNESEESWNWW"
        ]

        when: "cleaning spill"
        def resp = client.cleanSpill(input)

        then: "successful output is as expected"
        resp.responseBase.h.original.code == 200
        resp.responseData.finalPosition == [1,3]
        resp.responseData.patchesCleaned == 1
    }

    def "area size must be supplied"() {
        given: "input missing areaSize"

        def input = [
                "startingPosition" : [1, 2],
                "oilPatches" : [
                        [1, 0],
                        [2, 2],
                        [2, 3]
                ],
                "navigationInstructions" : "NNESEESWNWW"
        ]

        when: "cleaning spill"
        def resp = client.cleanSpill(input)

        then: "bad request"
        resp.responseBase.h.original.code == 400
    }

    def "oilPatches must be supplied"() {
        given: "input missing oilPatches"

        def input = [
                "areaSize" : [5, 5],
                "startingPosition" : [1, 2],
                "navigationInstructions" : "NNESEESWNWW"
        ]

        when: "cleaning spill"
        def resp = client.cleanSpill(input)

        then: "bad request"
        resp.responseBase.h.original.code == 400
    }

    def "navigationInstructions must be supplied"() {
        given: "input missing navigationInstructions"

        def input = [
                "areaSize" : [5, 5],
                "startingPosition" : [1, 2],
                "oilPatches" : [
                        [1, 0],
                        [2, 2],
                        [2, 3]
                ]
        ]

        when: "cleaning spill"
        def resp = client.cleanSpill(input)

        then: "bad request"
        resp.responseBase.h.original.code == 400
    }

    def "areaSize must be non-negative"() {
        given: "input with negative area size"

        def input = [
                "areaSize" : [-5, 5],
                "startingPosition" : [1, 2],
                "oilPatches" : [
                        [1, 0],
                        [2, 2],
                        [2, 3]
                ],
                "navigationInstructions" : "NNESEESWNWW"
        ]

        when: "cleaning spill"
        def resp = client.cleanSpill(input)

        then: "bad request"
        resp.responseBase.h.original.code == 400
    }

    def "empty navigation stays in same position"() {
        given: "input with no navigation"

        def input = [
                "areaSize" : [5, 5],
                "startingPosition" : [1, 2],
                "oilPatches" : [
                        [1, 0],
                        [2, 2],
                        [2, 3]
                ],
                "navigationInstructions" : ""
        ]

        when: "cleaning spill"
        def resp = client.cleanSpill(input)

        then: "end position is the same as input position"
        resp.responseBase.h.original.code == 200
        resp.responseData.finalPosition == [1, 2]
    }

    def "invalid navigation returns bad request"() {
        given: "input with navigation string containing invalid characters"

        def input = [
                "areaSize" : [5, 5],
                "startingPosition" : [1, 2],
                "oilPatches" : [
                        [1, 0],
                        [2, 2],
                        [2, 3]
                ],
                "navigationInstructions" : "thisIsInvalid"
        ]

        when: "cleaning spill"
        def resp = client.cleanSpill(input)

        then: "bad request"
        resp.responseBase.h.original.code == 400
    }

    def "oil patch out of bounds returns unprocessable entity"() {
        given: "input with an out of bounds oil patch"

        def input = [
                "areaSize" : [5, 5],
                "startingPosition" : [1, 2],
                "oilPatches" : [
                        [6, 6],
                        [2, 2],
                        [2, 3]
                ],
                "navigationInstructions" : "NNESEESWNWW"
        ]

        when: "cleaning spill"
        def resp = client.cleanSpill(input)

        then: "unprocessable entity"
        resp.responseBase.h.original.code == 422
    }
}
