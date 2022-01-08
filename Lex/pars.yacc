%{
#include <stdio.h>
#include <stdlib.h>

#define YYDEBUG 1 
%}

%token IDENTIFIER
%token NUMBER
%token CHARACTER
%token STRING

%token KEYWORD_IF
%token KEYWORD_ELSE
%token KEYWORD_WHILE
%token KEYWORD_PRINT
%token KEYWORD_READ
%token KEYWORD_DO
%token KEYWORD_INT
%token KEYWORD_CHAR

%token OPERATOR_ADDITION
%token OPERATOR_SUBTRACTION
%token OPERATOR_MULTIPLICATION
%token OPERATOR_DIVISION
%token OPERATOR_MODULO
%token OPERATOR_LESS
%token OPERATOR_GREATER
%token OPERATOR_EQUALS
%token OPERATOR_NOT_EQUALS
%token OPERATOR_LESS_EQUAL
%token OPERATOR_GREATER_EQUAL
%token OPERATOR_OR
%token OPERATOR_AND
%token OPERATOR_ASSIGN

%token SEPARATOR_CURLY_LEFT
%token SEPARATOR_CURLY_RIGHT
%token SEPARATOR_SEMI_COLON
%token SEPARATOR_ROUND_LEFT
%token SEPARATOR_ROUND_RIGHT
%token SEPARATOR_SQUARE_LEFT
%token SEPARATOR_SQUARE_RIGHT

%left '+' '-' '*' '/'

%start program 

%%

program : decllist cmpdstmt

decllist : declaration | declaration decllist

declaration : type IDENTIFIER SEPARATOR_SEMI_COLON

type : KEYWORD_INT | KEYWORD_CHAR 

cmpdstmt : SEPARATOR_CURLY_LEFT stmtlist SEPARATOR_CURLY_RIGHT

stmtlist : stmt | stmt stmtlist

stmt : simplestmt | structstmt

simplestmt : assignstmt | iostmt

iostmt : KEYWORD_READ iostmtaux | KEYWORD_PRINT iostmtaux

iostmtaux : SEPARATOR_ROUND_LEFT IDENTIFIER SEPARATOR_ROUND_RIGHT SEPARATOR_SEMI_COLON

assignstmt : IDENTIFIER OPERATOR_ASSIGN expression SEPARATOR_SEMI_COLON

expression : expression OPERATOR_ADDITION term | expression OPERATOR_SUBTRACTION term | term

term : term OPERATOR_DIVISION factor | term OPERATOR_MULTIPLICATION factor | term OPERATOR_MODULO factor | factor

factor : SEPARATOR_ROUND_LEFT expression SEPARATOR_ROUND_RIGHT | IDENTIFIER | NUMBER | CHARACTER | STRING

structstmt : cmpdstmt | ifstmt | whilestmt

ifstmt : KEYWORD_IF conditionblock stmtblock | KEYWORD_IF conditionblock stmtblock elsestmt

elsestmt : KEYWORD_ELSE stmtblock

whilestmt : KEYWORD_WHILE conditionblock KEYWORD_DO stmtblock

stmtblock : SEPARATOR_CURLY_LEFT stmtlist SEPARATOR_CURLY_RIGHT

conditionblock : SEPARATOR_ROUND_LEFT condition SEPARATOR_ROUND_RIGHT

condition : expression relation expression

relation : OPERATOR_LESS | OPERATOR_GREATER | OPERATOR_EQUALS | OPERATOR_NOT_EQUALS | OPERATOR_LESS_EQUAL | OPERATOR_GREATER_EQUAL | OPERATOR_OR | OPERATOR_AND

%%

void yyerror(char *s) {	
	printf("\n\t---- %s ----\n",s);
}

extern FILE *yyin;

int main(int argc, char **argv) {
	yydebug = 1;
	if (argc > 1) yyin = fopen(argv[1], "r");
	if (!yyparse()) fprintf(stderr, "\n\t---- OK. ----\n");
}