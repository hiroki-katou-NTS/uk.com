import text = nts.uk.resource.getText;
ko.components.register('kaf022-b', {
    viewModel: {
        createViewModel: function(params, componentInfo) {
            let vm = {
                itemListB4: ko.observableArray([
                    { code: 0, name: text('KAF022_421') },
                    { code: 0, name: text('KAF022_420') },
                ]),
                itemListB6: ko.observableArray([
                    { code: 0, name: text('KAF022_37') },
                    { code: 1, name: text('KAF022_136') },
                    { code: 2, name: text('KAF022_137') },
                ]),
                itemListB8: ko.observableArray([
                    { code: 0, name: text('KAF022_37') },
                    { code: 1, name: text('KAF022_36') },
                ]),
            };

            _.extend(vm, {
                selectedIdB4: params.selectedIdB4,
                selectedIdB8: params.selectedIdB8,
                selectedIdB10: params.selectedIdB10,
                selectedIdB12: params.selectedIdB12,
                selectedIdB6: params.selectedIdB6
            });
            return vm;
        }
    },
    template: `
    <!-- ko let: { $bv: $data, text: nts.uk.resource.getText } -->
    <div class="fixed-table-container" style="width: 900px">
        <table id="fixed-table-b1">
            <thead>
                <tr>
                    <th class="color-header" colspan="2" data-bind = "text: text('KAF022_119')"></th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td style="width: 270px" class="color-header" id="b3" data-bind = "text: text('KAF022_419')"></td>
                    <td style="width: 650px">
                        <div id="b4" tabindex="1" data-bind="ntsRadioBoxGroup: {
                            options: $bv.itemListB4,
                            optionsValue: 'code',
                            optionsText: 'name',
                            value: $bv.selectedIdB4,
                            enable: true}">
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="color-header" id="b5" data-bind = "text: text('KAF022_418')"></td>
                    <td>
                        <div id="b6" tabindex="2" data-bind="ntsRadioBoxGroup: {
                            options: $bv.itemListB6,
                            optionsValue: 'code',
                            optionsText: 'name',
                            value: $bv.selectedIdB6,
                            enable: true}">
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="color-header" id="b7" data-bind = "text: text('KAF022_418')"></td>
                    <td>
                        <div class="blockrow" id="b8" tabindex="3" data-bind="ntsRadioBoxGroup: {
                            options: $bv.itemListB8,
                            optionsValue: 'code',
                            optionsText: 'name',
                            value: $bv.selectedIdB8,
                            enable: true}">
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="color-header" id="b9" data-bind = "text: text('KAF022_428')"></td>
                    <td>
                        <div class="blockrow" id="b10" tabindex="3" data-bind="ntsRadioBoxGroup: {
                            options: $bv.itemListB8,
                            optionsValue: 'code',
                            optionsText: 'name',
                            value: $bv.selectedIdB10,
                            enable: true}">
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="color-header" id="b11" data-bind = "text: text('KAF022_429')"></td>
                    <td>
                        <div class="blockrow" id="b12" tabindex="4" data-bind="ntsRadioBoxGroup: {
                            options: $bv.itemListB8,
                            optionsValue: 'code',
                            optionsText: 'name',
                            value: $bv.selectedIdB12,
                            enable: true}">
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
    <div class="fixed-table-container" style="width: 900px; margin-top: 20px;">
        <table id="fixed-table-b2">
            <thead>
                <tr>
                    <th class="color-header" id="b2" colspan="2" data-bind = "text: text('KAF022_119')"></th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td style="width: 270px" class="color-header" id="b3" data-bind = "text: text('KAF022_419')"></td>
                    <td style="width: 650px">
                        <div id="b4" tabindex="1" data-bind="ntsRadioBoxGroup: {
                            options: $bv.itemListB4,
                            optionsValue: 'code',
                            optionsText: 'name',
                            value: $bv.selectedIdB4,
                            enable: true}">
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="color-header" id="b5" data-bind = "text: text('KAF022_418')"></td>
                    <td>
                        <div id="b6" tabindex="2" data-bind="ntsRadioBoxGroup: {
                            options: $bv.itemListB6,
                            optionsValue: 'code',
                            optionsText: 'name',
                            value: $bv.selectedIdB6,
                            enable: true}">
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="color-header" id="b7" data-bind = "text: text('KAF022_418')"></td>
                    <td>
                        <div class="blockrow" id="b8" tabindex="3" data-bind="ntsRadioBoxGroup: {
                            options: $bv.itemListB8,
                            optionsValue: 'code',
                            optionsText: 'name',
                            value: $bv.selectedIdB8,
                            enable: true}">
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="color-header" id="b9" data-bind = "text: text('KAF022_428')"></td>
                    <td>
                        <div class="blockrow" id="b10" tabindex="3" data-bind="ntsRadioBoxGroup: {
                            options: $bv.itemListB8,
                            optionsValue: 'code',
                            optionsText: 'name',
                            value: $bv.selectedIdB10,
                            enable: true}">
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="color-header" id="b11" data-bind = "text: text('KAF022_429')"></td>
                    <td>
                        <div class="blockrow" id="b12" tabindex="4" data-bind="ntsRadioBoxGroup: {
                            options: $bv.itemListB8,
                            optionsValue: 'code',
                            optionsText: 'name',
                            value: $bv.selectedIdB12,
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