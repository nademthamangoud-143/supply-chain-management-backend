$ErrorActionPreference = "Stop"

$baseUrl = "http://localhost:8081"

Write-Host ""
Write-Host "==================================================" -ForegroundColor Cyan
Write-Host "     SUPPLY CHAIN FULL CRUD API AUTOMATION" -ForegroundColor Cyan
Write-Host "==================================================" -ForegroundColor Cyan
Write-Host ""

# ==================================================
# RESULT TRACKING
# ==================================================

$results = @()

function Add-Result {
    param(
        [string]$Name,
        [bool]$Success,
        [string]$Message = ""
    )

    $script:results += [PSCustomObject]@{
        Test    = $Name
        Status  = if ($Success) { "PASS" } else { "FAIL" }
        Message = $Message
    }

    if ($Success) {
        Write-Host "$Name : PASS" -ForegroundColor Green
    }
    else {
        Write-Host "$Name : FAIL" -ForegroundColor Red

        if ($Message) {
            Write-Host "  $Message" -ForegroundColor DarkRed
        }
    }
}

# ==================================================
# API HELPER
# ==================================================

function Invoke-Api {
    param(
        [string]$Name,
        [string]$Method,
        [string]$Url,
        [object]$Body = $null
    )

    try {

        if ($null -ne $Body) {

            $jsonBody = $Body | ConvertTo-Json -Depth 10

            $response = Invoke-RestMethod `
                -Uri $Url `
                -Method $Method `
                -Headers $headers `
                -ContentType "application/json" `
                -Body $jsonBody
        }
        else {

            $response = Invoke-RestMethod `
                -Uri $Url `
                -Method $Method `
                -Headers $headers
        }

        Add-Result $Name $true

        return $response
    }
    catch {

        $message = $_.Exception.Message

        if ($_.ErrorDetails.Message) {
            $message = $_.ErrorDetails.Message
        }

        Add-Result $Name $false $message

        return $null
    }
}

# ==================================================
# LOGIN
# ==================================================

Write-Host "========== LOGIN ==========" -ForegroundColor Cyan

$loginBody = @{
    username = "productuser"
    password = "Product@123"
} | ConvertTo-Json

try {

    $loginResponse = Invoke-RestMethod `
        -Uri "$baseUrl/api/auth/login" `
        -Method Post `
        -ContentType "application/json" `
        -Body $loginBody

    $token = $loginResponse.token

    if ($token) {

        $headers = @{
            Authorization = "Bearer $token"
        }

        Add-Result "Authentication" $true
    }
    else {

        Add-Result "Authentication" $false "Token was not returned."
        exit
    }
}
catch {

    Add-Result "Authentication" $false $_.Exception.Message
    exit
}

# ==================================================
# GET EXISTING CATEGORY
# ==================================================

Write-Host ""
Write-Host "========== CATEGORY ==========" -ForegroundColor Cyan

$categories = Invoke-Api `
    "GET Categories" `
    "GET" `
    "$baseUrl/api/categories"

$categoryId = $null

if ($categories) {

    $categoryId = @($categories)[0].id
}

# If no category exists, create one
if (-not $categoryId) {

    $categoryName = "AUTO_TEST_CATEGORY_$(Get-Date -Format 'yyyyMMddHHmmss')"

    $categoryBody = @{
        name = $categoryName
        description = "Automated API test category"
        active = $true
    }

    $newCategory = Invoke-Api `
        "POST Category" `
        "POST" `
        "$baseUrl/api/categories" `
        $categoryBody

    if ($newCategory) {
        $categoryId = $newCategory.id
    }
}

if ($categoryId) {

    Invoke-Api `
        "GET Category By ID" `
        "GET" `
        "$baseUrl/api/categories/$categoryId" | Out-Null
}

# ==================================================
# PRODUCT CRUD
# ==================================================

Write-Host ""
Write-Host "========== PRODUCT CRUD ==========" -ForegroundColor Cyan

$timestamp = Get-Date -Format "yyyyMMddHHmmss"

$productBody = @{
    sku = "AUTO-SKU-$timestamp"
    name = "AUTO_TEST_PRODUCT"
    description = "Automated API test product"
    category = @{
        id = $categoryId
    }
    unitPrice = 1000
    reorderLevel = 10
    active = $true
}

$testProduct = Invoke-Api `
    "POST Product" `
    "POST" `
    "$baseUrl/api/products" `
    $productBody

$testProductId = $null

if ($testProduct) {

    $testProductId = $testProduct.id

    Invoke-Api `
        "GET Product By ID" `
        "GET" `
        "$baseUrl/api/products/$testProductId" | Out-Null

    $productUpdate = @{
        sku = "AUTO-SKU-$timestamp"
        name = "AUTO_TEST_PRODUCT_UPDATED"
        description = "Updated automated API test product"
        category = @{
            id = $categoryId
        }
        unitPrice = 1500
        reorderLevel = 5
        active = $true
    }

    Invoke-Api `
        "PUT Product" `
        "PUT" `
        "$baseUrl/api/products/$testProductId" `
        $productUpdate | Out-Null

    Invoke-Api `
        "DELETE Product" `
        "DELETE" `
        "$baseUrl/api/products/$testProductId" | Out-Null
}

# ==================================================
# WAREHOUSE - PERSISTENT TEST WAREHOUSE
# ==================================================

Write-Host ""
Write-Host "========== WAREHOUSE ==========" -ForegroundColor Cyan

$warehouseName = "AUTO_TEST_WAREHOUSE_" + (Get-Date -Format "yyyyMMddHHmmss")

$warehouseBody = @{
    name = $warehouseName
    location = "Hyderabad"
    managerName = "API Test Manager"
    capacity = 500
    active = $true
}

$testWarehouse = Invoke-Api `
    "POST Test Warehouse" `
    "POST" `
    "$baseUrl/api/warehouses" `
    $warehouseBody

$warehouseId = $null

if ($testWarehouse) {

    $warehouseId = $testWarehouse.id

    Invoke-Api `
        "GET Warehouse By ID" `
        "GET" `
        "$baseUrl/api/warehouses/$warehouseId" | Out-Null

    $warehouseUpdate = @{
        name = $warehouseName
        location = "Hyderabad"
        managerName = "Updated API Manager"
        capacity = 1000
        active = $true
    }

    Invoke-Api `
        "PUT Warehouse" `
        "PUT" `
        "$baseUrl/api/warehouses/$warehouseId" `
        $warehouseUpdate | Out-Null
}

# ==================================================
# SUPPLIER CRUD
# ==================================================

Write-Host ""
Write-Host "========== SUPPLIER CRUD ==========" -ForegroundColor Cyan

$supplierTimestamp = Get-Date -Format "yyyyMMddHHmmss"

$supplierBody = @{
    name = "AUTO_TEST_SUPPLIER_$supplierTimestamp"
    contactPerson = "Test Manager"
    email = "autotest$supplierTimestamp@example.com"
    phone = "9000000000"
    address = "Hyderabad"
    active = $true
}

$testSupplier = Invoke-Api `
    "POST Supplier" `
    "POST" `
    "$baseUrl/api/suppliers" `
    $supplierBody

$supplierId = $null

if ($testSupplier) {

    $supplierId = $testSupplier.id

    Invoke-Api `
        "GET Supplier By ID" `
        "GET" `
        "$baseUrl/api/suppliers/$supplierId" | Out-Null

    $supplierUpdate = @{
        name = $supplierBody.name
        contactPerson = "Updated Test Manager"
        email = $supplierBody.email
        phone = "9111111111"
        address = "Updated Hyderabad"
        active = $true
    }

    Invoke-Api `
        "PUT Supplier" `
        "PUT" `
        "$baseUrl/api/suppliers/$supplierId" `
        $supplierUpdate | Out-Null
}

# ==================================================
# INVENTORY CRUD
# ==================================================

Write-Host ""
Write-Host "========== INVENTORY CRUD ==========" -ForegroundColor Cyan

# Use existing product ID 2 because the temporary product
# has already been deleted above.

$inventoryProductId = 2

if ($warehouseId) {

    $inventoryBody = @{
        product = @{
            id = $inventoryProductId
        }
        warehouse = @{
            id = $warehouseId
        }
        quantity = 100
        reservedQuantity = 10
    }

    $inventoryRecord = Invoke-Api `
        "POST Inventory" `
        "POST" `
        "$baseUrl/api/inventory" `
        $inventoryBody

    $inventoryId = $null

    if ($inventoryRecord) {

        $inventoryId = $inventoryRecord.id

        Invoke-Api `
            "GET Inventory By ID" `
            "GET" `
            "$baseUrl/api/inventory/$inventoryId" | Out-Null

        Invoke-Api `
            "GET Inventory By Warehouse" `
            "GET" `
            "$baseUrl/api/inventory/warehouse/$warehouseId" | Out-Null

        Invoke-Api `
            "GET Inventory By Product" `
            "GET" `
            "$baseUrl/api/inventory/product/$inventoryProductId" | Out-Null

        $inventoryUpdate = @{
            product = @{
                id = $inventoryProductId
            }
            warehouse = @{
                id = $warehouseId
            }
            quantity = 150
            reservedQuantity = 20
        }

        Invoke-Api `
            "PUT Inventory" `
            "PUT" `
            "$baseUrl/api/inventory/$inventoryId" `
            $inventoryUpdate | Out-Null
    }
}

# ==================================================
# ORDER CRUD
# ==================================================

Write-Host ""
Write-Host "========== ORDER CRUD ==========" -ForegroundColor Cyan

$orderTimestamp = Get-Date -Format "yyyyMMddHHmmss"

if ($warehouseId) {

    $orderBody = @{
        orderNumber = "AUTO-ORDER-$orderTimestamp"
        customerName = "Automated Test Customer"
        customerEmail = "autotest$orderTimestamp@example.com"
        customerPhone = "9000000000"
        warehouse = @{
            id = $warehouseId
        }
        status = "PENDING"
        orderDate = (Get-Date).ToString("yyyy-MM-dd")
        totalAmount = 5000
    }

    $testOrder = Invoke-Api `
        "POST Order" `
        "POST" `
        "$baseUrl/api/orders" `
        $orderBody

    $orderId = $null

    if ($testOrder) {

        $orderId = $testOrder.id

        Invoke-Api `
            "GET Order By ID" `
            "GET" `
            "$baseUrl/api/orders/$orderId" | Out-Null

        Invoke-Api `
            "GET Order By Number" `
            "GET" `
            "$baseUrl/api/orders/number/$($orderBody.orderNumber)" | Out-Null

        Invoke-Api `
            "GET Orders By Status" `
            "GET" `
            "$baseUrl/api/orders/status/PENDING" | Out-Null

        Invoke-Api `
            "GET Orders By Warehouse" `
            "GET" `
            "$baseUrl/api/orders/warehouse/$warehouseId" | Out-Null

        $orderUpdate = @{
            orderNumber = $orderBody.orderNumber
            customerName = "Updated Automated Customer"
            customerEmail = $orderBody.customerEmail
            customerPhone = "9111111111"
            warehouse = @{
                id = $warehouseId
            }
            status = "CONFIRMED"
            orderDate = (Get-Date).ToString("yyyy-MM-dd")
            totalAmount = 6000
        }

        Invoke-Api `
            "PUT Order" `
            "PUT" `
            "$baseUrl/api/orders/$orderId" `
            $orderUpdate | Out-Null
    }
}

# ==================================================
# PURCHASE ORDER CRUD
# ==================================================

Write-Host ""
Write-Host "========== PURCHASE ORDER CRUD ==========" -ForegroundColor Cyan

$poTimestamp = Get-Date -Format "yyyyMMddHHmmss"

if ($supplierId -and $warehouseId) {

    $poBody = @{
        poNumber = "AUTO-PO-$poTimestamp"
        supplier = @{
            id = $supplierId
        }
        warehouse = @{
            id = $warehouseId
        }
        status = "PENDING"
        orderDate = (Get-Date).ToString("yyyy-MM-dd")
        expectedDate = (Get-Date).AddDays(7).ToString("yyyy-MM-dd")
        totalAmount = 10000
    }

    $testPO = Invoke-Api `
        "POST Purchase Order" `
        "POST" `
        "$baseUrl/api/purchase-orders" `
        $poBody

    $poId = $null

    if ($testPO) {

        $poId = $testPO.id

        Invoke-Api `
            "GET Purchase Order By ID" `
            "GET" `
            "$baseUrl/api/purchase-orders/$poId" | Out-Null

        Invoke-Api `
            "GET PO By Number" `
            "GET" `
            "$baseUrl/api/purchase-orders/number/$($poBody.poNumber)" | Out-Null

        Invoke-Api `
            "GET PO By Status" `
            "GET" `
            "$baseUrl/api/purchase-orders/status/PENDING" | Out-Null

        Invoke-Api `
            "GET PO By Supplier" `
            "GET" `
            "$baseUrl/api/purchase-orders/supplier/$supplierId" | Out-Null

        Invoke-Api `
            "GET PO By Warehouse" `
            "GET" `
            "$baseUrl/api/purchase-orders/warehouse/$warehouseId" | Out-Null

        $poUpdate = @{
            poNumber = $poBody.poNumber
            supplier = @{
                id = $supplierId
            }
            warehouse = @{
                id = $warehouseId
            }
            status = "APPROVED"
            orderDate = (Get-Date).ToString("yyyy-MM-dd")
            expectedDate = (Get-Date).AddDays(10).ToString("yyyy-MM-dd")
            totalAmount = 12000
        }

        Invoke-Api `
            "PUT Purchase Order" `
            "PUT" `
            "$baseUrl/api/purchase-orders/$poId" `
            $poUpdate | Out-Null
    }
}

# ==================================================
# SHIPMENT CRUD
# ==================================================

Write-Host ""
Write-Host "========== SHIPMENT CRUD ==========" -ForegroundColor Cyan

$shipmentTimestamp = Get-Date -Format "yyyyMMddHHmmss"

if ($orderId) {

    $shipmentBody = @{
        shipmentNumber = "AUTO-SHIP-$shipmentTimestamp"
        order = @{
            id = $orderId
        }
        status = "CREATED"
        carrierName = "AUTO TEST CARRIER"
        trackingNumber = "AUTO-TRACK-$shipmentTimestamp"
        expectedDeliveryDate = (Get-Date).AddDays(5).ToString("yyyy-MM-dd")
        shippingAddress = "Hyderabad, Telangana"
    }

    $testShipment = Invoke-Api `
        "POST Shipment" `
        "POST" `
        "$baseUrl/api/shipments" `
        $shipmentBody

    $shipmentId = $null

    if ($testShipment) {

        $shipmentId = $testShipment.id

        Invoke-Api `
            "GET Shipment By ID" `
            "GET" `
            "$baseUrl/api/shipments/$shipmentId" | Out-Null

        Invoke-Api `
            "GET Shipment By Number" `
            "GET" `
            "$baseUrl/api/shipments/number/$($shipmentBody.shipmentNumber)" | Out-Null

        Invoke-Api `
            "GET Shipment By Status" `
            "GET" `
            "$baseUrl/api/shipments/status/CREATED" | Out-Null

        Invoke-Api `
            "GET Shipment By Order" `
            "GET" `
            "$baseUrl/api/shipments/order/$orderId" | Out-Null

        $shipmentUpdate = @{
            shipmentNumber = $shipmentBody.shipmentNumber
            order = @{
                id = $orderId
            }
            status = "IN_TRANSIT"
            carrierName = "UPDATED TEST CARRIER"
            trackingNumber = $shipmentBody.trackingNumber
            expectedDeliveryDate = (Get-Date).AddDays(7).ToString("yyyy-MM-dd")
            shippingAddress = "Updated Hyderabad, Telangana"
        }

        Invoke-Api `
            "PUT Shipment" `
            "PUT" `
            "$baseUrl/api/shipments/$shipmentId" `
            $shipmentUpdate | Out-Null
    }
}

# ==================================================
# STOCK MOVEMENT
# ==================================================

Write-Host ""
Write-Host "========== STOCK MOVEMENT ==========" -ForegroundColor Cyan

if ($warehouseId) {

    $movementBody = @{
        product = @{
            id = $inventoryProductId
        }
        warehouse = @{
            id = $warehouseId
        }
        movementType = "IN"
        quantity = 25
        referenceType = "AUTOMATED_API_TEST"
        referenceId = $orderId
        notes = "Automated API test stock movement"
    }

    $movement = Invoke-Api `
        "POST Stock Movement" `
        "POST" `
        "$baseUrl/api/stock-movements" `
        $movementBody

    Invoke-Api `
        "GET Stock Movements By Product" `
        "GET" `
        "$baseUrl/api/stock-movements/product/$inventoryProductId" | Out-Null

    Invoke-Api `
        "GET Stock Movements By Warehouse" `
        "GET" `
        "$baseUrl/api/stock-movements/warehouse/$warehouseId" | Out-Null

    Invoke-Api `
        "GET Stock Movements By Product + Warehouse" `
        "GET" `
        "$baseUrl/api/stock-movements/product/$inventoryProductId/warehouse/$warehouseId" | Out-Null
}

# ==================================================
# CLEANUP
# ==================================================

Write-Host ""
Write-Host "========== CLEANUP ==========" -ForegroundColor Cyan

# Shipment first because it depends on Order
if ($shipmentId) {

    Invoke-Api `
        "DELETE Shipment" `
        "DELETE" `
        "$baseUrl/api/shipments/$shipmentId" | Out-Null
}

# Order
if ($orderId) {

    Invoke-Api `
        "DELETE Order" `
        "DELETE" `
        "$baseUrl/api/orders/$orderId" | Out-Null
}

# Inventory
if ($inventoryId) {

    Invoke-Api `
        "DELETE Inventory" `
        "DELETE" `
        "$baseUrl/api/inventory/$inventoryId" | Out-Null
}

# Purchase Order
if ($poId) {

    Invoke-Api `
        "DELETE Purchase Order" `
        "DELETE" `
        "$baseUrl/api/purchase-orders/$poId" | Out-Null
}

# Supplier
if ($supplierId) {

    Invoke-Api `
        "DELETE Supplier" `
        "DELETE" `
        "$baseUrl/api/suppliers/$supplierId" | Out-Null
}

# ==================================================
# FINAL SUMMARY
# ==================================================

Write-Host ""
Write-Host "==================================================" -ForegroundColor Cyan
Write-Host "                FINAL TEST SUMMARY" -ForegroundColor Cyan
Write-Host "==================================================" -ForegroundColor Cyan

$passed = @($results | Where-Object { $_.Status -eq "PASS" }).Count
$failed = @($results | Where-Object { $_.Status -eq "FAIL" }).Count

Write-Host ""
Write-Host "TOTAL TESTS : $($results.Count)"
Write-Host "PASSED      : $passed" -ForegroundColor Green
Write-Host "FAILED      : $failed" -ForegroundColor Red

Write-Host ""

if ($failed -eq 0) {

    Write-Host "ALL AUTOMATED API TESTS PASSED!" -ForegroundColor Green
}
else {

    Write-Host "SOME TESTS FAILED. Check the FAIL messages above." -ForegroundColor Red
}

Write-Host ""
Write-Host "Note: The automated Stock Movement test creates a test record." -ForegroundColor Yellow
Write-Host "StockMovementController has no DELETE endpoint, so that record is retained." -ForegroundColor Yellow
Write-Host ""