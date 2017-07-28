  CREATE TABLE ORDER (
                PROFILE VARCHAR2(40 CHAR),
                ORDER_ID VARCHAR2(40 CHAR),
                ORDER_STATUS VARCHAR2(40 CHAR),
                CREATION_DATE DATE,
                LAST_MODIFIED_DATE DATE,
                CONSTRAINT PK_ORDER PRIMARY KEY (PROFILE)
  )
                PARTITION BY LIST(STATUS)
                (
                                PARTITION P_COMPLETE VALUES ('placed'),
                                PARTITION P_ACTIVE VALUES ('new','in_progress'),
                                PARTITION P_MAX VALUES (DEFAULT)
                )
   ENABLE ROW MOVEMENT;


  CREATE TABLE ITEMS (
                PROFILE VARCHAR2(40 CHAR),
                SKU_ID VARCHAR2(40 CHAR),
                SKU_QUANTITY NUMBER(5),
                CONSTRAINT PK_ITEMS PRIMARY KEY (PROFILE, SKU_ID),
                CONSTRAINT FK_ITEMS FOREIGN KEY (PROFILE) REFERENCES ORDER (PROFILE) ON DELETE CASCADE ENABLE
   )
   PARTITION BY REFERENCE (FK_SHOPPING_CART_ITEMS)
   ENABLE ROW MOVEMENT;