$.ig.NtsNumberEditor = $.ig.EditorProvider.extend({
    /**
     * Create editor.
     */
    createEditor: function (callbacks, key, editorOptions, tabIndex, format, element) {
        var randomId = nts.uk.util.randomId();
        var editorWrapper = $('<div id="edtiorWrapper"><input id="' + randomId + '"/></div>');
        this.editor = {};
        this.editor.element = editorWrapper;
        this.editor.koObject = ko.observable(undefined);
        ko.applyBindingsToNode($('input', editorWrapper).get(0), {
            ntsNumberEditor: $.extend({
                value: this.editor.koObject,
            }, editorOptions)
        });
        // Must call text change to enable ok button on grid.
        $('input', editorWrapper).on('input', function (evt) {
            callbacks.textChanged(evt, {}, key);
        });
        this._super(callbacks, key, editorOptions, tabIndex, format, element);
        return editorWrapper;
    },
    /**
     * No need to impl?
     */
    attachErrorEvents: function (errorShowing, errorShown, errorHidden) { },
    /**
      * Get value function.
      * Get current object value.
      */
    getValue: function () {
        return this.editor.koObject();
    },
    /**
     * Set value function.
     * Set current object value.
     */
    setValue: function (val) {
        this.editor.koObject(val);
    },
    /**
     * Set size for control.
     */
    setSize: function (width, height) {
        $('input', this.editor.element).width(width - 22);
        $('input', this.editor.element).height(height - 10);
    },
    /**
     * Destroy.
     */
    destroy: function () {
        // Need destroy.
    },
    /**
     * Call validate function.
     */
    validate: function () {
        $('input', this.editor.element).ntsEditor('validate');
        return !$('span', this.editor.element).hasClass('error');
    },
    /**
     * Is valid method
     */
    isValid: function () {
        return !$('span', this.editor.element).hasClass('error');
    }
});
