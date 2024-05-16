package network;

import java.util.HashMap;
import java.util.Map;

public class ResponseBuilder {
    private final Map<String, StringBuilder> responseMap = new HashMap<>();
    public String get(String username) {
        String s = responseMap.getOrDefault(username, new StringBuilder()).toString();
        clear(username);
        return s;
    }

    public void add(String username, String s) {
        if (!responseMap.containsKey(username)) {
            responseMap.put(username, new StringBuilder());
        }
        StringBuilder strBuilder = responseMap.get(username);
        strBuilder.append(s).append("\n");
        responseMap.put(username, strBuilder);
    }

    private void clear(String username) {
        responseMap.put(username, new StringBuilder());
    }
}
