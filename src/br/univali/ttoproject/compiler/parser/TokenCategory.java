package br.univali.ttoproject.compiler.parser;

public enum TokenCategory {
    Keyword {
        public int id() {
            return 0;
        }

        @Override
        public String toString() {
            return String.format("Keyword(%d)", id());
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
    LiteralConstant {
        public int id() {
            return 3;
        }

        @Override
        public String toString() {
            return String.format("LiteralConstant(%d)", id());
        }
    },
    IntegerConstant {
        public int id() {
            return 4;
        }

        @Override
        public String toString() {
            return String.format("IntegerConstant(%d)", id());
        }
    },
    RealConstant {
        public int id() {
            return 5;
        }

        @Override
        public String toString() {
            return String.format("RealConstant(%d)", id());
        }
    },
    Unknown {
        public int id() {
            return 6;
        }

        @Override
        public String toString() {
            return String.format("Unknown(%d)", id());
        }
    }
}
