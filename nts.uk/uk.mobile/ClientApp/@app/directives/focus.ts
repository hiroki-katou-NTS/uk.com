const focus = {
    // directive definition
    inserted: function (el: HTMLInputElement) {
        el.focus()
    }
};

export { focus };