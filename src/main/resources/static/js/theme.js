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

    const formError = document.querySelector(".form-error");

    if (formError) {
        setTimeout(() => {
            formError.scrollIntoView({
                behavior: "smooth",
                block: "center"
            });
        }, 150);
    }

    const observationButtons = document.querySelectorAll(".table-action.observation");
    const observationModal = document.getElementById("observationModal");
    const observationModalText = document.getElementById("observationModalText");
    const closeObservationButtons = document.querySelectorAll("[data-close-observation]");

    observationButtons.forEach((button) => {
        button.addEventListener("click", () => {
            const observationText = button.getAttribute("data-observacion");

            if (observationModal && observationModalText) {
                observationModalText.textContent = observationText;
                observationModal.classList.add("is-open");
                observationModal.setAttribute("aria-hidden", "false");
            }
        });
    });

    closeObservationButtons.forEach((button) => {
        button.addEventListener("click", closeObservationModal);
    });

    document.addEventListener("keydown", (event) => {
        if (event.key === "Escape") {
            closeObservationModal();
        }
    });

    function closeObservationModal() {
        if (observationModal) {
            observationModal.classList.remove("is-open");
            observationModal.setAttribute("aria-hidden", "true");
        }
    }
});