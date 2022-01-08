%{
    #include <math.h>
    #include <stdlib.h>
    #include "y.tab.h"
%}

WHITESPACE              [ \r\t\n]
ZERO                    [0]
DIGIT                   [0-9]
NONZERO_DIGIT           [1-9]
LETTER                  [a-zA-Z]
ALPHANUMERIC            [a-zA-Z_0-9]
MINUS                   "-"

NUMBER                  {MINUS}?{NONZERO_DIGIT}{DIGIT}*|{ZERO}
CHARACTER               '{ALPHANUMERIC}'
STRING                  \"{ALPHANUMERIC}+\"

IDENTIFIER              {LETTER}{ALPHANUMERIC}*

%%

if    { return KEYWORD_IF; }
else  { return KEYWORD_ELSE; }
while { return KEYWORD_WHILE; }
print { return KEYWORD_PRINT; }
read  { return KEYWORD_READ; }
do    { return KEYWORD_DO; }
int   { return KEYWORD_INT; }
char  { return KEYWORD_CHAR; }

"+" { return OPERATOR_ADDITION; }
"-" { return OPERATOR_SUBTRACTION; }
"*" { return OPERATOR_MULTIPLICATION; }
"/" { return OPERATOR_DIVISION; }
"%" { return OPERATOR_MODULO; }
"<" { return OPERATOR_LESS; }
">" { return OPERATOR_GREATER; }
"==" { return OPERATOR_EQUALS; }
"!=" { return OPERATOR_NOT_EQUALS; }
"<=" { return OPERATOR_LESS_EQUAL; }
">=" { return OPERATOR_GREATER_EQUAL; }
"||" { return OPERATOR_OR; }
"&&" { return OPERATOR_AND; }
"=" { return OPERATOR_ASSIGN; }

"{" { return SEPARATOR_CURLY_LEFT; }
"}" { return SEPARATOR_CURLY_RIGHT; }
";" { return SEPARATOR_SEMI_COLON; }
"(" { return SEPARATOR_ROUND_LEFT; }
")" { return SEPARATOR_ROUND_RIGHT; }
"[" { return SEPARATOR_SQUARE_LEFT; }
"]" { return SEPARATOR_SQUARE_RIGHT; }

{NUMBER} { return NUMBER; }
{CHARACTER} { return CHARACTER; }
{STRING} { return STRING; }
{IDENTIFIER} { return IDENTIFIER; }

{WHITESPACE}+ {}

. { printf("Unrecognized token: %s\n", yytext); }

%%