# ETL process for people 

## Main description
The objective of this program is to create a data warehouse 
for torre's users and opportunities.

Following a snowflake model in MySQL. (Upgrade: Redshift or other data warehousing database)

### Missing functionalities
* Add opportunities to the database was planned but dute to time concerns it wasn't implemented on time.
* Add 'openTo' field to person
* Add verified boolean field to person model

### Future work
* Incremental updates
* Dashboard for data visualization made in BI software such as data studio
* Use and OLAP database such as Redshift.
* Manage database access as .env variables
