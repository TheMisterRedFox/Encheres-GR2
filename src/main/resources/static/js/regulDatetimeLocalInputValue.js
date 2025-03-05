function regulDatetimeLocalInputValue(){
    document.querySelectorAll('input[type="datetime-local"]').forEach(input => {
        const inputValue = input.getAttribute('value');
        if (inputValue) {
            let date = inputValue.trim(); // Ex: "07/03/2025 11:13"

            let match = date.match(/^(\d{2})\/(\d{2})\/(\d{4}) (\d{2}):(\d{2})$/);
            if (match) {
                let [, day, month, year, hours, minutes] = match;
                let formattedDate = `${year}-${month}-${day}T${hours}:${minutes}`;
                input.value = formattedDate; // Appliquer le format correct
            }
        }
    });
}

regulDatetimeLocalInputValue();