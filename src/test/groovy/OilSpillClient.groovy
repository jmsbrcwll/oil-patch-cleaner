import groovyx.net.http.ContentType
import groovyx.net.http.RESTClient

/**
 * Created by jimbracewell on 06/06/2018.
 */
class OilSpillClient {

    def client

    def setupClient(port) {
        client = new RESTClient("http://localhost:$port/oil-spill")

        client.handler.failure = { resp, data ->
            resp.data = data
            return resp
        }
    }

    def cleanSpill(body) {
        return client.post(
                body: body,
                requestContentType: ContentType.JSON
        )
    }
}
