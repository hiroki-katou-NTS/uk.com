module nts.custom.component {

    window["ko"].components.register('base-date', {
        template: `
        <div class="form-group">
            <div data-bind="ntsFormLabel: {text: text('CPS001_35')}"></div>
            <div tabindex="17" data-bind="ntsDatePicker: { enable: !!employeeId(), name: text('CPS001_35'), value: standardDate, dateFormat: 'YYYY/MM/DD'}"></div>
            <button tabindex="18" data-bind="enable: !!employeeId(), click: function() { id.valueHasMutated() }, text: text('CPS001_37')"></button>
        </div>`,
        viewModel: function(params: any) {
            $.extend(params, {
                text: nts.uk.resource.getText
            });

            return params;
        }
    });
}