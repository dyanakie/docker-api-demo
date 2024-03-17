package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Map;

//        Input1 : “Jean-Louis”
//        Input2 : “Jean-Charles Mignard”
//        Input3 : “external”
//        Input4 : “peoplespheres.fr”
//        Input5 : “fr”
//        jl.jccharlesmignard@external.peoplespheres.fr

// EXPLANATION
// OPERATION:takeSubstring / FROM:input1 / PARAMS 1 START ALL => jl
// OPERATION:concatenate / . => .
// OPERATION:takeSubstring / FROM:input1 / PARAMS 1 START 1 => j
// OPERATION:takeSubstring / FROM:input1 / PARAMS 1 START 2 => c
// OPERATION:takeWord / FROM:input2 / PARAMS 2 => charles
// OPERATION:takeWord / FROM:input2 / PARAMS 3 => mignard
// OPERATION:concatenate / @ => @
// OPERATION:concatenate / FROM:input3 => external
// OPERATION:concatenate / . => .
// OPERATION:concatenate / FROM:input4 => peoplespheres.fr
// WHOLE:  OPERATION:takeSubstring / FROM:input1 / PARAMS 1 START ALL ~ OPERATION:concatenate / . ~ OPERATION:takeSubstring / FROM:input2 / PARAMS 1 START 1 ~ OPERATION:takeSubstring / FROM:input2 / PARAMS 1 START 2 ~ OPERATION:takeWord / FROM:input2 / PARAMS 2 ~ OPERATION:takeWord / FROM:input2 / PARAMS 3 ~ OPERATION:concatenate / @ ~ OPERATION:concatenate / FROM:input3 ~ OPERATION:concatenate / . ~ OPERATION:concatenate / FROM:input4

@Service
@RequiredArgsConstructor
public class EmailGeneratorService {
    public String generateEmail(Map<String, String> inputs, String expression) {

        final var commands = expression.split(" ~ ");
        var email = new StringBuilder();

        for (var command : commands) {
            var subTasks = command.split(" / ");
            var operationName = subTasks[0].split(":")[1];
            switch (operationName) {
                case "takeSubstring":
                    var source = subTasks[1].split(":")[1];
                    var params = subTasks[2];
                    var words = inputs.get(source).split("[ -]");
                    email.append(takeSubstring(words, params));
                    break;
                case "takeWord":
                    var source2 = subTasks[1].split(":")[1];
                    var params2 = subTasks[2];
                    var words2 = inputs.get(source2).split("[ -]");
                    email.append(takeWord(words2, params2));
                    break;
                case "concatenate":
                    var params3 = subTasks[1];
                    var newPart = params3.contains("FROM:") ? inputs.get(params3.trim().split(":")[1]) : params3;
                    email.append(newPart);
                    break;
                default:
                    System.out.println("Error: uknown operation");
                    break;
            }
        }

        return email.toString().toLowerCase();

    }

    private String takeWord(String[] words, String params) {
        int index = Integer.parseInt(params.split(" ")[1].trim()) - 1;
        return words[index];
    }

    private String takeSubstring(String[] words, String params) {
        String[] parts = params.split(" ");
        int length = Integer.parseInt(parts[1]);
        String direction = parts[2];
        String targetWords = parts[3];

        StringBuilder result = new StringBuilder();

        if (targetWords.equals("ALL")) {
            for (String word : words) {
                if (direction.equals("START")) {
                    result.append(word.substring(0, length));
                } else if (direction.equals("END")) {
                    result.append(word.substring(word.length() - length));
                }
            }
        } else {
            int index = Integer.parseInt(targetWords) - 1;
            String word = words[index];
            if (direction.equals("START")) {
                result.append(word.substring(0, length));
            } else if (direction.equals("END")) {
                result.append(word.substring(word.length() - length));
            }
        }

        return result.toString();
    }
}
