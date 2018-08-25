import text = nts.uk.resource.getText;

ko.components.register('kaf022-d', {
    viewModel: {
        createViewModel: function(params, componentInfo) {
            let vm = {
                itemListD15: ko.observableArray([
                    { code: 0, name: text('KAF022_391') },
                    { code: 1, name: text('KAF022_392') }
                ]),
                itemListD13: ko.observableArray([
                    { code: 1, name: text('KAF022_389') },
                    { code: 0, name: text('KAF022_390') }
                ]),
                itemListD16: ko.observableArray([
                    { code: 1, name: text('KAF022_36') },
                    { code: 0, name: text('KAF022_37') }
                ])
            };

            _.extend(vm, {
                selectedIdD15: params.selectedIdD15,
                selectedIdD13: params.selectedIdD13,
                selectedIdD16: params.selectedIdD16,
                
                texteditorD9: params.texteditorD9,
                valueD10: params.valueD10,
                enableD11: params.enableD11,
                
                texteditorD12: params.texteditorD12,
                valueD10_1: params.valueD10_1,
                enableD11_1: params.enableD11_1,
                
                selectedIdD8: params.selectedIdD8,
            });

            return vm;
        }
    }, template: `
    <!-- ko let: { $dv: $data, text: nts.uk.resource.getText } -->
    <div class="fixed-table-container" style="width: 900px; margin-top: 20px;">
        <table id="fixed-table-d1">
            <thead>
                <tr>
                    <th class="color-header" id="b13" colspan="2" data-bind = "text: text('KAF022_178')"></th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td style="width: 270px" class="color-header" id="d14" data-bind = "text: text('KAF022_186')"></td>
                    <td style="width: 650px">
                        <div id="d15" tabindex="1" data-bind="ntsRadioBoxGroup: {
                            options: $dv.itemListD15,
                            optionsValue: 'code',
                            optionsText: 'name',
                            value: $dv.selectedIdD15,
                            enable: true}">
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="color-header" id="d6" data-bind = "text: text('KAF022_388')"></td>
                    <td>
                        <div class="blockdeki" id="d13" tabindex="2" data-bind="ntsRadioBoxGroup: {
                            options: $dv.itemListD13,
                            optionsValue: 'code',
                            optionsText: 'name',
                            value: $dv.selectedIdD13,
                            enable: true}">
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="color-header" id="d7" data-bind = "text: text('KAF022_182')"></td>
                    <td>
                        <div class="blockrow" id="d16" tabindex="3" data-bind="ntsRadioBoxGroup: {
                            options: $dv.itemListD16,
                            optionsValue: 'code',
                            optionsText: 'name',
                            value: $dv.selectedIdD16,
                            enable: true}">
                        </div>
                    </td>
                </tr>
                <tr>
                    <td style="border-bottom: none" class="color-header" id="d4">
                        <div data-bind="ntsFormLabel: {
                                constraint: 'Comment',
                                inline: true,
                                text: text('KAF022_179')
                            }"></div>
                    </td>
                    <td>
                        <input tabindex="2" id = "d9" data-bind="ntsTextEditor: {
                                name: text('KAF022_179'),
                                value: $dv.texteditorD9.value,
                                constraint: 'Comment',
                                required: false,
                                enable: true,
                                readonly: false
                            }" />
                        <div tabindex="3" id = "d10" id="colorpicker" data-bind="ntsColorPicker: {
                                value: $dv.valueD10, 
                                enable: true,
                                width: 40
                            }"/>
                        <div tabindex="4" id = "d11" data-bind="ntsCheckBox: { 
                                checked: $dv.enableD11, 
                                text: text('KAF022_185')
                            }"></div>   
                    </td>
                </tr>
                <tr>
                    <td style="border-bottom: none" class="color-header" id="d4">
                        <div data-bind="ntsFormLabel: {
                                constraint: 'Comment',
                                inline: true,
                                text: text('KAF022_180')
                            }"></div>
                    </td>
                    <td>
                        <input tabindex="2" id="d12_1" data-bind="ntsTextEditor: {
                                name: text('KAF022_180'),
                                value: $dv.texteditorD12.value, 
                                constraint: 'Comment',
                                required: false,
                                enable: true,
                                readonly: false
                            }" />
                        <div tabindex="3" id="colorpicker" data-bind="ntsColorPicker: {
                                value: $dv.valueD10_1, 
                                name: text('KAF022_183'),
                                enable: true,
                                width: 40
                            }"/>
                        <div tabindex="4" id="d12_3" data-bind="ntsCheckBox: { 
                                checked: $dv.enableD11_1, 
                                text: text('KAF022_185')
                            }"></div>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>

    <div class="fixed-table-container" style="width: 900px; margin-top: 20px;">
        <table id="fixed-table-d2">
            <thead>
                <tr>
                    <th class="color-header" id="d17" colspan="2" data-bind = "text: text('KAF022_142')"></th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td style="width: 270px" class="color-header" id="d3" data-bind = "text: text('KAF022_150')"></td>
                    <td style="width: 650px">
                        <div class="blockrow" id="d8" tabindex="1" data-bind="ntsRadioBoxGroup: {
                            options: $dv.itemListD16,
                            optionsValue: 'code',
                            optionsText: 'name',
                            value: $dv.selectedIdD8,
                            enable: true}">
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
    <!-- /ko -->
`
});