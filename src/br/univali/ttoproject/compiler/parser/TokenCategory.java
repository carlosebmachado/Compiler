package br.univali.ttoproject.compiler.parser;

public enum TokenCategory {
    Keyword {
        public int id() {
            return 0;
        }

        @Override
        public String toString() {
            return "Keyword";
        }
    },
    SpecialSymbol {
        public int id() {
            return 1;
        }

        @Override
        public String toString() {
            return "SpecialSymbol";
        }
    },
    Identifier {
        public int id() {
            return 2;
        }

        @Override
        public String toString() {
            return "Identifier";
        }
    },
    Literal {
        public int id() {
            return 3;
        }

        @Override
        public String toString() {
            return "Literal";
        }
    },
    Operator {
        public int id() {
            return 4;
        }

        @Override
        public String toString() {
            return "Operator";
        }
    },
    Unknown {
        public int id() {
            return 5;
        }

        @Override
        public String toString() {
            return "Unknown";
        }
    }
}
