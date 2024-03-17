# docker-api-demo

### Custom Parsing Logic

I have decided to think over and create my own expression parser.
You can find examples in the email service and I will put them here as well.

### Commands

- takeSubstring - takes substring form one or many words from start or end
- takeWord - takes whole word
- concatenate - concatenates string or whole input
- " ~ " is between commands

### Examples

- Input1 : “Jean-Louis”
- Input2 : “Jean-Charles Mignard”
- Input3 : “external”
- Input4 : “peoplespheres.fr”
- Input5 : “fr” 
   <br><br>
  Working with this inputs we apply the following commands:
- OPERATION:takeSubstring / FROM:input1 / PARAMS 1 START ALL => jl
- OPERATION:concatenate / . => .
- OPERATION:takeSubstring / FROM:input1 / PARAMS 1 START 1 => j
- OPERATION:takeSubstring / FROM:input1 / PARAMS 1 START 2 => c
- OPERATION:takeWord / FROM:input2 / PARAMS 2 => charles
- OPERATION:takeWord / FROM:input2 / PARAMS 3 => mignard
- OPERATION:concatenate / @ => @
- OPERATION:concatenate / FROM:input3 => external
- OPERATION:concatenate / . => .
- OPERATION:concatenate / FROM:input4 => peoplespheres.fr
  <br><br>
  Result: jl.jccharlesmignard@external.peoplespheres.fr
  <br><br>
  Result is also packed in a json as per requirement.

  <br><br>
  Full example expression query param: 
- OPERATION:takeSubstring / FROM:input1 / PARAMS 1 START ALL ~ OPERATION:concatenate / . ~ OPERATION:takeSubstring / FROM:input2 / PARAMS 1 START 1 ~ OPERATION:takeSubstring / FROM:input2 / PARAMS 1 START 2 ~ OPERATION:takeWord / FROM:input2 / PARAMS 2 ~ OPERATION:takeWord / FROM:input2 / PARAMS 3
  ~ OPERATION:concatenate / @ ~ OPERATION:concatenate / FROM:input3 ~ OPERATION:concatenate / . ~ OPERATION:concatenate / FROM:input4