package org.knowm.xdropwizard.resources;

import java.io.InputStream;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/file")
@Produces(MediaType.APPLICATION_JSON)
public class FileUploadResource {

  private final Logger logger = LoggerFactory.getLogger(FileUploadResource.class);

  @POST
  @Path("/upload")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  public Response uploadFile(
      @FormDataParam("file") InputStream uploadedInputStream,
      @FormDataParam("file") FormDataContentDisposition fileDetail) {

    String fileName = fileDetail.getFileName();

    String output = "File received : " + fileName;

    logger.info(output);

    return Response.status(200).entity(output).build();
  }
}
