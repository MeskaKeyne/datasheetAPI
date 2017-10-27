package be.ste.st.datasheet.rest.server;

import be.ste.st.datasheet.rest.service.TimeSheetService;

public class Server {
	public static void main(String[] args) throws Exception {
		java.net.URI uri = new java.net.URI("http://localhost:8080/timesheet/");
		
		org.glassfish.jersey.server.ResourceConfig config
			= new org.glassfish.jersey.server.ResourceConfig();
		config.register(TimeSheetService.class);
		
		org.glassfish.jersey.jdkhttp.JdkHttpServerFactory.createHttpServer(uri, config);
		
		System.out.println("Server is ready !");
	}
}
