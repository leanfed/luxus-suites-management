document.addEventListener("DOMContentLoaded", () => {
    const body = document.body;
    const buttons = document.querySelectorAll("[data-theme-toggle]");

    const savedTheme = localStorage.getItem("luxus-theme");

    if (savedTheme === "light") {
        body.classList.add("light-theme");
    }

    updateButtons();

    buttons.forEach((button) => {
        button.addEventListener("click", () => {
            body.classList.toggle("light-theme");

            const currentTheme = body.classList.contains("light-theme") ? "light" : "dark";
            localStorage.setItem("luxus-theme", currentTheme);

            updateButtons();
        });
    });

    function updateButtons() {
        const isLight = body.classList.contains("light-theme");

        buttons.forEach((button) => {
            button.textContent = isLight ? "Modo oscuro" : "Modo claro";
        });
    }
});