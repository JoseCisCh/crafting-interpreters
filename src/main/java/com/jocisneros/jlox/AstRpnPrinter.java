
package com.jocisneros.jlox;

import com.jocisneros.jlox.Expr.Binary;
import com.jocisneros.jlox.Expr.Grouping;
import com.jocisneros.jlox.Expr.Literal;
import com.jocisneros.jlox.Expr.Unary;

class AstRpnPrinter implements Expr.Visitor<String> {

    String print(Expr expr) {
        return expr.accept(this);
    }

    @Override
    public String visitBinaryExpr(Binary expr) {
        return rpnNotate(expr.operator.lexeme,expr.left, expr.right);
    }

    @Override
    public String visitGroupingExpr(Grouping expr) {
        return rpnNotate(null, expr.expression);
    }

    @Override
    public String visitLiteralExpr(Literal expr) {
        if(expr.value != null) {
            return expr.value.toString() + " ";
        } else {
            return "nil";
        }
    }

    @Override
    public String visitUnaryExpr(Unary expr) {
        return "";
    }

    private String rpnNotate(String operator, Expr... exprs) {
        String notation = "";

        for(Expr expr: exprs) {
            //notation += " ";
            notation += expr.accept(this);
        }
        if(operator != null) {
            notation += operator + " ";
        }

         return notation;
    }

    public static void main(String[] args) {
        Expr expression = new Expr.Binary(
            new Grouping(new Expr.Binary(
                new Expr.Literal(1),
                new Token(TokenType.PLUS, "+", null, 1),
                new Expr.Literal(2)
            )),
            new Token(TokenType.STAR, "*", null, 1),
            new Grouping(new Expr.Binary(
                new Expr.Literal(4),
                new Token(TokenType.MINUS, "-", null, 1),
                new Expr.Literal(3)
            ))
        );

        System.out.println(new AstRpnPrinter().print(expression));
    }
}
