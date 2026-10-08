# Supply Chain Management - Automated Continuation

This version continues the existing project and adds REST controllers for:

- Products: `/api/products`
- Suppliers: `/api/suppliers`
- Warehouses: `/api/warehouses`
- Inventory: `/api/inventory`
- Stock movements: `/api/stock-movements`
- Customer orders: `/api/orders`
- Purchase orders: `/api/purchase-orders`
- Shipments: `/api/shipments`

## Run

Open a terminal in the project folder and run:

```powershell
cd "C:\Users\N.Thaman Goud\OneDrive\Desktop\supply-chain\supply-chain-management"
.\mvnw.cmd spring-boot:run
```

If Maven is installed globally, `mvn spring-boot:run` also works.

The application is configured for port `8081` and database `supply_chain_db`.

## Authentication

1. `POST /api/auth/register`
2. `POST /api/auth/login`
3. Copy the returned JWT token.
4. For protected APIs use the header:

`Authorization: Bearer YOUR_TOKEN`

The source project was not rebuilt in this environment because Maven dependencies could not be downloaded from Maven Central. Run the Maven command locally to compile against your installed JDK/Maven environment.
