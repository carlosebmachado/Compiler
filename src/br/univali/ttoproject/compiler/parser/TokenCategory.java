package br.univali.ttoproject.compiler.parser;

public enum TokenCategory {
    Keyword {
        public int id() {
            return 0;
        }

        @Override
        public String toString() {
            return String.format("Keyword(%i)", id());
        }
    },
    SpecialSymbol {
        public int id() {
            return 1;
        }

        @Override
        public String toString() {
            return String.format("SpecialSymbol(%d)", id());
        }
    },
    Identifier {
        public int id() {
            return 2;
        }

        @Override
        public String toString() {
            return String.format("Identifier(%d)", id());
        }
    },
    Literal {
        public int id() {
            return 3;
        }

        @Override
        public String toString() {
            return String.format("Literal(%d)", id());
        }
    },
    Operator {
        public int id() {
            return 4;
        }

        @Override
        public String toString() {
            return String.format("Operator(%d)", id());
        }
    },
    Unknown {
        public int id() {
            return 5;
        }

        @Override
        public String toString() {
            return String.format("Unknown(%d)", id());
        }
    }
}
