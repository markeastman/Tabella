package uk.me.eastmans.tabella.tests;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import org.kohsuke.args4j.*;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by meastman on 06/01/16.
 */
public class PostBallotResultsTest {

    private static Arguments arguments = new Arguments();

    public final static void main(String[] args) throws Exception {
        if (!processArgs(args))
            System.exit( -1 );

        BasicCookieStore cookieStore = new BasicCookieStore();
        CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultCookieStore(cookieStore)
                .build();

        try {
            HttpUriRequest login = RequestBuilder.post()
                    .setUri(new URI("http://localhost:28080/login"))
                    .addParameter("email", "u15@eastmans.me.uk")
                    .addParameter("password", "password")
                    .build();
            CloseableHttpResponse response2 = httpclient.execute(login);
            try {
                HttpEntity entity = response2.getEntity();

                System.out.println("Login form get: " + response2.getStatusLine());
                EntityUtils.consume(entity);

                System.out.println("Post logon cookies:");
                List<Cookie> cookies = cookieStore.getCookies();
                if (cookies.isEmpty()) {
                    System.out.println("None");
                } else {
                    for (int i = 0; i < cookies.size(); i++) {
                        System.out.println("- " + cookies.get(i).toString());
                    }
                }
            } finally {
                response2.close();
            }
/*
            // Now get the home page
            try {
                HttpGet httpget = new HttpGet("http://localhost:28080/home");

                System.out.println("Executing request " + httpget.getRequestLine());

                // Create a custom response handler
                ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                    @Override
                    public String handleResponse(
                            final HttpResponse response) throws ClientProtocolException, IOException {
                        int status = response.getStatusLine().getStatusCode();
                        if (status >= 200 && status < 300) {
                            HttpEntity entity = response.getEntity();
                            return entity != null ? EntityUtils.toString(entity) : null;
                        } else {
                            throw new ClientProtocolException("Unexpected response status: " + status);
                        }
                    }

                };
                String responseBody = httpclient.execute(httpget, responseHandler);
                System.out.println("----------------------------------------");
                System.out.println(responseBody);
            } finally {
                httpclient.close();
            }
*/
            Random r = new Random();
            // For now it does not matter if we submit for the same user multiple answers
            for (int i = 0; i < arguments.loopCount; i++) {
                float latitude = r.nextFloat() * 120 - 40;
                float longitude = r.nextFloat() * 360 - 180;
                int answerIndex = r.nextInt(arguments.answerUpperLimit);

                HttpUriRequest postAnswer = RequestBuilder.post()
                        .setUri(new URI("http://localhost:28080/ballot/answer"))
                        .addParameter("id", String.valueOf(arguments.ballotId))
                        .addParameter("latitude", String.valueOf(latitude))
                        .addParameter("longitude", String.valueOf(longitude))
                        .addParameter("answerIndex", String.valueOf(answerIndex))
                        .build();

                CloseableHttpResponse response3 = httpclient.execute(postAnswer);
                try {
                    HttpEntity entity = response3.getEntity();

                    System.out.println("Post Ballot Answer post: " + response3.getStatusLine());
                    EntityUtils.consume(entity);
                    Thread.sleep(arguments.waitMillis);
                } finally {
                    response3.close();
                }
            }

        } finally {
            httpclient.close();
        }
    }

    private static boolean processArgs( String[] args ) throws Exception
    {

        CmdLineParser parser = new CmdLineParser(arguments);
        try {
            // parse the arguments.
            parser.parseArgument(args);

            // you can parse additional arguments if you want.
            // parser.parseArgument("more","args");

            // after parsing arguments, you should check
            // if enough arguments are given.

        } catch( CmdLineException e ) {
            // if there's a problem in the command line,
            // you'll get this exception. this will report
            // an error message.
            System.err.println(e.getMessage());
            System.err.println("java PostBallotResultsTest [options...] arguments...");
            // print the list of available options
            parser.printUsage(System.err);
            System.err.println();

            // print option sample. This is useful some time
            System.err.println("  Example: java PostBallotResultsTest"+parser.printExample(OptionHandlerFilter.ALL));

            return false;
        }
        return true;
    }

    static class Arguments {
        @Option(name="-id",usage="the ballot id to answer")
        long ballotId = 3;

        @Option(name="-limit",usage="the answer index limit, random will be [0,limit)")
        int answerUpperLimit = 3;

        @Option(name="-loop",usage="how many times to loop")
        int loopCount = 20;

        @Option(name="-wait",usage="the number of millis to wait between answers")
        int waitMillis = 200;

        // receives other command line parameters than options
        @Argument
        private List<String> arguments = new ArrayList<String>();
    }
}
