import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    int num = 0;
    HashSet<String> words = new HashSet<String>();
    public String handleRequest(URI url) {
        String path = url.getPath();
        if(path.contains("/add")){
            String[] word = url.getQuery().split("=");
            words.add(word[1]);
            return "Word added: " + word[1];
        } else if (path.contains("/search")){
            String[] search = url.getQuery().split("=");
            String results = "the search results are: ";
            for (String i: words){
                if(i.contains(search[1])){
                    results = results.concat(i);
                    results = results.concat(", ");
                }
            }
            results = results.substring(0, results.length()-2);
            return results;
        } else {
            return "404 not found";
        }

    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
