$.ig.NtsNumberEditor = $.ig.EditorProvider.extend({
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
        $('input', editorWrapper).on('input', function (evt) {
            callbacks.textChanged(evt, {}, key);
        });
        this._super(callbacks, key, editorOptions, tabIndex, format, element);
        return editorWrapper;
    },
    attachErrorEvents: function (errorShowing, errorShown, errorHidden) { },
    getValue: function () {
        return this.editor.koObject();
    },
    setValue: function (val) {
        this.editor.koObject(val);
    },
    setSize: function (width, height) {
        $('input', this.editor.element).width(width - 22);
        $('input', this.editor.element).height(height - 10);
    },
    destroy: function () {
    },
    validate: function () {
        $('input', this.editor.element).ntsEditor('validate');
        return !$('span', this.editor.element).hasClass('error');
    },
    isValid: function () {
        return !$('span', this.editor.element).hasClass('error');
    }
});
