import text = nts.uk.resource.getText;

ko.components.register('kaf022-k', {
    viewModel: {
        createViewModel: function(params, componentInfo) {
            let vm = {
                itemListK12: ko.observableArray([
                    { code: 1, name: text('KAF022_100') },
                    { code: 0, name: text('KAF022_101') },
                    { code: 2, name: text('KAF022_270') }
                ]),
                itemListK13: ko.observableArray([
                    { code: 1, name: text('KAF022_75') },
                    { code: 0, name: text('KAF022_82') }
                ]),
                itemListK14: ko.observableArray([
                    { code: 1, name: text('KAF022_272') },
                    { code: 0, name: text('KAF022_273') }
                ]),
                itemListK16: ko.observableArray([
                    { code: 0, name: text('KAF022_173') },
                    { code: 1, name: text('KAF022_175') }
                ]),
                itemListK22: ko.observableArray([
                    { code: 0, name: text('KAF022_173') },
                    { code: 1, name: text('KAF022_174') },
                    { code: 2, name: text('KAF022_175') }
                ]),
            };

            _.extend(vm, {
                selectedIdK12: params.selectedIdK12,
                selectedIdK13: params.selectedIdK13,
                selectedIdK14: params.selectedIdK14,
                selectedIdK15: params.selectedIdK15,
                selectedIdK16: params.selectedIdK16,
                selectedIdK21: params.selectedIdK21,
                selectedIdK22: params.selectedIdK22,
                valueK18: params.valueK18,
                valueK18_1: params.valueK18_1,
                enableK19: params.enableK19,
                enableK19_1: params.enableK19_1,
                texteditorK17: params.texteditorK17,
                texteditorK20: params.texteditorK20
            });

            return vm;
        }
    },
    template: `
    <!-- ko let: { $kv: $data, text: nts.uk.resource.getText } -->
    <div class="fixed-table-container">
        <table id="fixed-table-k">
            <thead>
                <tr>
                    <th class="color-header" colspan="2" data-bind="text: text('KAF022_259')"></th>
                </tr>
            </thead>
            <tbody style="max-height: 536px;">
                <tr>
                    <td style="width: 250px" class="color-header" id="k3" data-bind="text: text('KAF022_260')"></td>
                    <td style="width: 700px" class="k12-radio_1">
                        <div tabindex="1" data-bind="ntsRadioBoxGroup: {
                                options: itemListK12,
                                optionsValue: 'code',
                                optionsText: 'name',
                                value: selectedIdK12,
                                enable: true
                            }">
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="color-header" id="k4" data-bind="text: text('KAF022_261')"></td>
                    <td class="k13-radio_1">
                        <div tabindex="2" data-bind="ntsRadioBoxGroup: {
                                options: itemListK13,
                                optionsValue: 'code',
                                optionsText: 'name',
                                value: selectedIdK13,
                                enable: true
                            }">
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="color-header" id="k5" data-bind="text: text('KAF022_262')"></td>
                    <td class="k14-radio_1">
                        <div tabindex="3" data-bind="ntsRadioBoxGroup: {
                                options: itemListK14,
                                optionsValue: 'code',
                                optionsText: 'name',
                                value: selectedIdK14,
                                enable: true
                            }">
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="color-header" id="k6" data-bind="text: text('KAF022_263')"></td>
                    <td class="k13-radio_1">
                        <div tabindex="4" data-bind="ntsRadioBoxGroup: {
                                options: itemListK13,
                                optionsValue: 'code',
                                optionsText: 'name',
                                value: selectedIdK15,
                                enable: true
                            }">
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="color-header" id="k7" data-bind="text: text('KAF022_264')"></td>
                    <td class="k16-radio_1">
                        <div tabindex="5" data-bind="ntsRadioBoxGroup: {
                                options: itemListK16,
                                optionsValue: 'code',
                                optionsText: 'name',
                                value: selectedIdK16,
                                enable: true
                            }">
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="color-header" id="k8">
                        <div data-bind="ntsFormLabel: {
                                    constraint: 'Comment', 
                                    inline: true,
                                    text: text('KAF022_265')
                                }"></div>
                    </td>
                    <td>
                        <input tabindex="6" data-bind="ntsTextEditor: {
                                name: text('KAF022_265'),
                                value: texteditorK17.value,
                                constraint: 'Comment',
                                required: false,
                                enable: true,
                                readonly: false
                            }" />
                        <div tabindex="7" id="colorpicker" data-bind="ntsColorPicker: {
                                value: $kv.valueK18, 
                                enable: true,
                                width: 40
                            }" />
                        <div tabindex="8" data-bind="ntsCheckBox: { 
                                checked: $kv.enableK19,
                                text: text('KAF022_185')
                            }"></div>
                    </td>
                </tr>
                <tr>
                    <td class="color-header" id="k9">
                        <div data-bind="ntsFormLabel: {
                                    constraint: 'Comment',
                                    inline: true,
                                    text: text('KAF022_266')
                                }"></div>
                    </td>
                    <td>
                        <input tabindex="9" data-bind="ntsTextEditor: {
                                name: text('KAF022_266'),
                                value: texteditorK20.value,
                                constraint: 'Comment',
                                required: false,
                                enable: true,
                                readonly: false
                            }" />
                        <div tabindex="10" id="colorpicker" data-bind="ntsColorPicker: {
                                value: $kv.valueK18_1,
                                enable: true, width: 40
                            }" />
                        <div tabindex="11" data-bind="ntsCheckBox: { 
                                checked: $kv.enableK19_1,
                                text: text('KAF022_185')
                            }"></div>
                    </td>
                </tr>
                <tr>
                    <td class="color-header" id="k10" data-bind="text: text('KAF022_267')"></td>
                    <td class="k14-radio_1">
                        <div tabindex="12" data-bind="ntsRadioBoxGroup: {
                                options: itemListK14,
                                optionsValue: 'code',
                                optionsText: 'name',
                                value: selectedIdK21,
                                enable: true
                            }">
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="color-header" id="k11" data-bind="text: text('KAF022_268')"></td>
                    <td class="k16-radio_1">
                        <div tabindex="13" data-bind="ntsRadioBoxGroup: {
                                options: itemListK22,
                                optionsValue: 'code',
                                optionsText: 'name',
                                value: selectedIdK22,
                                enable: true
                            }">
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
    <!-- /ko -->
    `
});