%{
    #include <math.h>
    #include <stdlib.h>
    #include <string.h>
%}

WHITESPACE              [ \r\t\n]
ZERO                    [0]
DIGIT                   [0-9]
NONZERO_DIGIT           [1-9]
LETTER                  [a-zA-Z]
ALPHANUMERIC            [a-zA-Z_0-9]
MINUS                   "-"

KEYWORD_IF              "if"
KEYWORD_ELSE            "else"
KEYWORD_WHILE           "while"
KEYWORD_PRINT           "print"
KEYWORD_DO              "do"
KEYWORD_INT             "int"
KEYWORD_CHAR            "char"
KEYWORD_TYPE            {KEYWORD_INT}|{KEYWORD_CHAR}

OPERATOR_ADDITION       "+"
OPERATOR_SUBTRACTION    "-"
OPERATOR_MULTIPLICATION "*"
OPERATOR_DIVISION       "/"
OPERATOR_MODULO         "%"
OPERATOR_ARITHMETIC     {OPERATOR_ADDITION}|{OPERATOR_SUBTRACTION}|{OPERATOR_MULTIPLICATION}|{OPERATOR_DIVISION}|{OPERATOR_MODULO}
OPERATOR_LESS           "<"
OPERATOR_GREATER        ">"
OPERATOR_EQUALS         "=="
OPERATOR_NOT_EQUALS     "!="
OPERATOR_LESS_EQUAL     "<="
OPERATOR_GREATER_EQUAL  ">="
OPERATOR_OR             "||"
OPERATOR_AND            "&&"
OPERATOR_RELATIONAL     {OPERATOR_LESS}|{OPERATOR_GREATER}|{OPERATOR_EQUALS}|{OPERATOR_NOT_EQUALS}|{OPERATOR_LESS_EQUAL}|{OPERATOR_GREATER_EQUAL}|{OPERATOR_OR}|{OPERATOR_AND}
OPERATOR_ASSIGN         "="

NUMBER                  {MINUS}?{NONZERO_DIGIT}{DIGIT}*|{ZERO}
CHARACTER               '{ALPHANUMERIC}'
STRING                  \"{ALPHANUMERIC}+\"

SEPARATOR               [{};()[\]]
IDENTIFIER              {LETTER}{ALPHANUMERIC}*
OPERATOR                {OPERATOR_ARITHMETIC}|{OPERATOR_RELATIONAL}|{OPERATOR_ASSIGN}
KEYWORD                 {KEYWORD_IF}|{KEYWORD_ELSE}|{KEYWORD_WHILE}|{KEYWORD_PRINT}|{KEYWORD_DO}|{KEYWORD_TYPE}
CONST                   {NUMBER}|{CHARACTER}|{STRING}

%%

{KEYWORD} {
    // printf("Keyword: %s\n", yytext);
    insert_pif(yytext, -1);
}
{IDENTIFIER} {
    // printf("Identifier: %s\n", yytext);
    int index = insert_st(yytext);
    insert_pif(yytext, index - 1);
}
{CONST} {
    // printf("Const: %s\n", yytext);
    int index = insert_st(yytext);
    insert_pif(yytext, index - 1);
}
{OPERATOR} {
    // printf("Operator: %s\n", yytext);
    insert_pif(yytext, -1);
}
{SEPARATOR} {
    // printf("Separator: %s\n", yytext);
    insert_pif(yytext, -1);
}
{WHITESPACE}+ {
    // whitespace
}
. {
    printf("Unrecognized token: %s\n", yytext);
}

%%

char* symboltable[128];
size_t symboltable_size = 0;

typedef struct {
    char* token;
    int index;
} PifEntry;

PifEntry pif[128];
size_t pif_size = 0;

int insert_st(char* token) {
    for (size_t index = 0; index < symboltable_size; index++) {
        if(!strcmp(token, symboltable[index])) {
            return index + 1;
        }
    }
    char* copy = (char*)malloc(sizeof(char) * (strlen(token) + 1));
    strcpy(copy, token);
    symboltable[symboltable_size] = copy;
    return ++symboltable_size;
}

void insert_pif(char* token, int index) {
    char* copy = (char*)malloc(sizeof(char) * (strlen(token) + 1));
    strcpy(copy, token);
    pif[pif_size] = (PifEntry) { .token = copy, .index = index };
    ++pif_size;
}

void print_all() {
    printf("\tSymbol Table:\n");
    for (size_t i = 0; i < symboltable_size; i++) {
        printf("%s -> %d\n", symboltable[i], i);
    }
    printf("\tPIF:\n");
    for (size_t i = 0; i < pif_size; i++) {
        printf("%s -> %d\n", pif[i].token, pif[i].index);
    }
}

void delete_all() {
    for (size_t i = 0; i < symboltable_size; i++) {
        free(symboltable[i]);
    }
    for (size_t i = 0; i < pif_size; i++) {
        free(pif[i].token);
    }
}

int main(int argc, char **argv) {
    if (argc > 1) {
        yyin = fopen(argv[1], "r");
    } else {
        yyin = stdin;
    }
    yylex();
    print_all();
}