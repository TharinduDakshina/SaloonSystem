package modle;

public class Customer {
        private String customerNic;
        private String customerName;
        private String customerAge;
        private String hairStyle;
        private String customerCount;

        public Customer(String customerNic, String customerName, String customerAge, String hairStyle, String customerCount) {
                this.setCustomerNic(customerNic);
                this.setCustomerName(customerName);
                this.setCustomerAge(customerAge);
                this.setHairStyle(hairStyle);
                this.setCustomerCount(customerCount);
        }

        public Customer() {
        }

        public String getCustomerNic() {
                return customerNic;
        }

        public void setCustomerNic(String customerNic) {
                this.customerNic = customerNic;
        }

        public String getCustomerName() {
                return customerName;
        }

        public void setCustomerName(String customerName) {
                this.customerName = customerName;
        }

        public String getCustomerAge() {
                return customerAge;
        }

        public void setCustomerAge(String customerAge) {
                this.customerAge = customerAge;
        }

        public String getHairStyle() {
                return hairStyle;
        }

        public void setHairStyle(String hairStyle) {
                this.hairStyle = hairStyle;
        }

        public String getCustomerCount() {
                return customerCount;
        }

        public void setCustomerCount(String customerCount) {
                this.customerCount = customerCount;
        }

        @Override
        public String toString() {
                return "Customer{" +
                        "customerNic='" + customerNic + '\'' +
                        ", customerName='" + customerName + '\'' +
                        ", customerAge='" + customerAge + '\'' +
                        ", hairStyle='" + hairStyle + '\'' +
                        ", customerCount='" + customerCount + '\'' +
                        '}';
        }
}
