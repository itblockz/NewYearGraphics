package csvjoin;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CSVJoin {
    public static List<Map<String, String>> join(String file1, String file2, String commonField) throws IOException {
        List<Map<String, String>> data1 = parse(file1);
        List<Map<String, String>> data2 = parse(file2);
            
        return data1.stream()
            .map(map1 -> {
                String commonValue = map1.get(commonField);
                Map<String, String> matchingMap2 = data2.stream()
                    .filter(map2 -> map2.get(commonField).equals(commonValue))
                    .findFirst()
                    .orElse(null);
                if (matchingMap2 != null) {
                    map1.putAll(matchingMap2);
                }
                return map1;
            }).collect(Collectors.toList());
    }

    private static List<Map<String, String>> parse(String file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String[] header = reader.readLine().split(",");
            
            return reader.lines()
                .map(line -> {
                    String[] values = line.split(",");
                    return IntStream.range(0, header.length)
                        .boxed()
                        .collect(Collectors.toMap(
                            i -> header[i],
                            i -> i < values.length ? values[i] : ""
                        ));
                }).collect(Collectors.toList());
        }
    }
}
