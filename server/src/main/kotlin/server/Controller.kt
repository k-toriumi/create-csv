package server

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Consumes
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.server.types.files.StreamedFile
import server.service.Service
import java.io.ByteArrayInputStream
import java.net.URLEncoder
import javax.inject.Inject

@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Controller("/")
class Controller {

    @Inject
    lateinit var service: Service

    val headers: Map<CharSequence, CharSequence> =
            mutableMapOf(
                    "X-Frame-Options" to "deny",
                    "X-XSS-Protection" to "1; mode=block",
                    "X-Content-Type-Options" to "nosniff"
            )

    @Post("/download")
    fun download(): HttpResponse<StreamedFile> {
        var csv = service.download()
        val file = StreamedFile(csv, MediaType.APPLICATION_OCTET_STREAM_TYPE)
        val filename = URLEncoder.encode("book_info.csv", "UTF-8")
        return HttpResponse
                .ok(file)
                .header(
                        "Content-Disposition",
                        "attachment;filename=book_info.csv;filename*=UTF-8''$filename"
                ).headers(headers)
    }
}

