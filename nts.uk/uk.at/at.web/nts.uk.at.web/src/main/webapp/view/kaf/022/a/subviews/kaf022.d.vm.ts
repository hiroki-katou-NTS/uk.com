import text = nts.uk.resource.getText;

ko.components.register('kaf022-d', {
    viewModel: {
        createViewModel: function(params, componentInfo) {
            let vm = {
            };
            return vm;
        }
    }, template: `
    <!-- ko let: { $dv: $data, text: nts.uk.resource.getText } -->
    <div class="fixed-table-container">
        <table id="fixed-table-d">
            <thead>
                <tr>
                    <th class="color-header"colspan="2" data-bind="text: text('KAF022_178')"></th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td class="color-header" id="d3" data-bind="text: text('KAF022_150')"></td>
                    <td>
                        <div tabindex="1" data-bind="ntsRadioBoxGroup: {
                                options: itemListD8,
                                optionsValue: 'code',
                                optionsText: 'name',
                                value: selectedIdD8,
                                enable: true
                            }"></div>
                    </td>
                </tr>
                <tr>
                    <td class="color-header" id="d4">
                        <div data-bind="ntsFormLabel: { 
                                constraint: 'Comment',
                                inline:true,
                                text: text('KAF022_179')
                            }"></div>
                    </td>
                    <td>
                        <div  data-bind="with: texteditorD9">
                            <input tabindex="2" data-bind="ntsTextEditor: {
                                    name: text('KAF022_179'),
                                    value: value,
                                    constraint: constraint,
                                    option: option,
                                    required: required,
                                    enable: true,
                                    readonly: readonly
                                }" />
                            <div tabindex="3" id="colorpicker" data-bind="ntsColorPicker: {
                                    value: $parent.valueD10, 
                                    enable: true,
                                    width: 40
                                }"/>
                            <div tabindex="4" data-bind="ntsCheckBox: { 
                                    checked: $parent.enableD11, 
                                    text: text('KAF022_185')
                                }"></div>
                        </div>
                        
                    </td>
                </tr>
                <tr>
                    <td class="color-header" id="d5">
                        <div data-bind="ntsFormLabel: { 
                                constraint: 'Comment',
                                inline:true,
                                text: text('KAF022_180')
                            }"></div>
                    </td>
                    <td>
                        <div data-bind="with: texteditorD12">
                            <input tabindex="5" data-bind="ntsTextEditor: {
                                    name: text('KAF022_180'),
                                    value: value,
                                    constraint: constraint,
                                    option: option,
                                    required: required,
                                    enable: true,
                                    readonly: readonly
                                }" />
                            <div tabindex="6" id="colorpicker" data-bind="ntsColorPicker: {
                                        value: $parent.valueD10_1,
                                        enable: true, 
                                        width: 40
                                    }"></div>
                            <div tabindex="7" data-bind="ntsCheckBox: { 
                                        checked: $parent.enableD11_1,
                                        text: text('KAF022_185')
                                    }"></div>
                        </div>                        
                    </td>
                </tr>
                <tr>
                    <td class="color-header" id="d6" data-bind="text: text('KAF022_181')"></td>
                    <td>
                        <div  class="ntsControl">
                            <label class="ntsRadioBox">
                                <span tabindex="8">
                                    <input  type="radio" name="textRadioD13" data-bind="
                                            checkedValue: 1,
                                            checked: selectedValueD13, 
                                            enable: true
                                        " />
                                    <span class="box"></span>
                                    <span class="label" data-bind="text: text('KAF022_75')"></span>
                                </span>
                                <span>(</span>
                                <span class="label" data-bind="text: text('KAF022_186')"></span>
                                <span tabindex="9" data-bind="ntsRadioBoxGroup: {
                                        options: itemListD15,
                                        optionsValue: 'code',
                                        optionsText: 'name',
                                        value: selectedIdD15,
                                        enable: enableD15
                                    }"></span>
                                <span>)</span>
                            </label>
                            <div>
                                <label class="ntsRadioBox" tabindex="8">
                                    <input type="radio" name="textRadioD13" data-bind="
                                            checkedValue: 0,
                                            checked: selectedValueD13,
                                            enable: true
                                        " />
                                    <span class="box"></span>
                                    <span class="label" data-bind="text: text('KAF022_82')"></span>
                                </label>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="color-header" id="d7" data-bind="text: text('KAF022_182')"></td>
                    <td>
                        <div tabindex="10" data-bind="ntsRadioBoxGroup: {
                                options: itemListD8,
                                optionsValue: 'code',
                                optionsText: 'name',
                                value: selectedIdD16,
                                enable: true
                            }"></div>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
    <!-- ko -->
`
});