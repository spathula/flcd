lex:
	lex spec.lex
	byacc -d pars.yacc
	gcc lex.yy.c y.tab.c -o comp.out -lfl

run:
	./comp.out p3.txt

all: lex run