// Confirm logout
function confirmLogout() {
    return confirm("Are you sure you want to log out?");
}

// Update date and time
function updateDateTime() {
    const now = new Date();
    const options = {weekday: 'short', year: 'numeric', month: 'short', day: 'numeric'};
    const date = now.toLocaleDateString('en-US', options);
    const time = now.getHours().toString().padStart(2, '0') + ':' + now.getMinutes().toString().padStart(2, '0');
    const dtElem = document.getElementById('currentDateTime');
    if (dtElem)
        dtElem.textContent = date + ' | ' + time;
}

// Initialize dashboard
function initDashboard() {
    updateDateTime();
    setInterval(updateDateTime, 60000);
    initLiveSearch();
}

function showDashboard() {
    const contentArea = document.getElementById("contentArea");
    contentArea.innerHTML = `
        <div class="cards">
            <div class="card">
                <h3>Create New Bill</h3>
                <p>Generate bills quickly for customers.</p>
                <a href="calculateBill.jsp">Start Billing</a>
            </div>
            <div class="card">
                <h3>Recent Processes</h3>
                <p>View your most recent transactions and activities.</p>
                <a href="RecentProcesses.jsp">View</a>
            </div>
            <div class="card">
                <h3>Manage Customers</h3>
                <p>Add or view customer details easily.</p>
                <a href="Customer/Customers.jsp">View Customers</a>
            </div>
            <div class="card">
                <h3>Manage Books</h3>
                <p>Add new books to inventory.</p>
                <a href="Item/Item.jsp">Add Book</a>
            </div>
        </div>
    `;
}


// Load AJAX content
function loadContent(url) {
    fetch(url)
            .then(response => response.text())
            .then(data => {
                const parser = new DOMParser();
                const doc = parser.parseFromString(data, "text/html");
                const newContent = doc.getElementById("contentArea");

                const mainContent = document.getElementById("mainContent");
                if (newContent) {
                    // Case: Customers.jsp / Item.jsp / dashboard_content.jsp (with wrapper)
                    mainContent.innerHTML = newContent.innerHTML;
                    mainContent.dataset.source = newContent.dataset.source;
                } else {
                    // Fallback: if file has no #contentArea
                    mainContent.innerHTML = data;
                    mainContent.dataset.source = url;
                }

                initLiveSearch();
            });
}
// Initialize live search
function initLiveSearch() {
    const searchInput = document.querySelector("#mainContent .search-bar input[name='search']");
    if (!searchInput)
        return;

    searchInput.addEventListener("keyup", function () {
        const keyword = searchInput.value;
        const mainContent = document.getElementById("mainContent");
        const currentPage = mainContent.dataset.source || "";

        let fetchUrl = "";
        if (currentPage.includes("Customer")) {
            fetchUrl = "Customer/Customers.jsp?search=" + encodeURIComponent(keyword);
        } else if (currentPage.includes("Item")) {
            fetchUrl = "Item/Item.jsp?search=" + encodeURIComponent(keyword);
        }

        if (fetchUrl) {
            fetch(fetchUrl)
                    .then(response => response.text())
                    .then(data => {
                        const parser = new DOMParser();
                        const doc = parser.parseFromString(data, "text/html");
                        const newTable = doc.querySelector("#tableContainer");
                        const tableContainer = mainContent.querySelector("#tableContainer");
                        if (newTable && tableContainer) {
                            tableContainer.innerHTML = newTable.innerHTML;
                        }
                    });
        }
    });
}

window.addEventListener("DOMContentLoaded", initDashboard);
