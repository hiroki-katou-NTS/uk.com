import text = nts.uk.resource.getText;
ko.components.register('kaf022-b', {
    viewModel: {
        createViewModel: function(params, componentInfo) {
            let vm = {
                itemListB4: ko.observableArray([
                    new ItemModel(1, nts.uk.resource.getText('KAF022_420')),
                    new ItemModel(0, nts.uk.resource.getText('KAF022_421'))
                ]),
                itemListB6: ko.observableArray([
                    new ItemModel(2, nts.uk.resource.getText('KAF022_137')),
                    new ItemModel(1, nts.uk.resource.getText('KAF022_136')),
                    new ItemModel(0, nts.uk.resource.getText('KAF022_37'))
                ]),
                itemListB8: ko.observableArray([
                    new ItemModel(1, nts.uk.resource.getText('KAF022_36')),
                    new ItemModel(0, nts.uk.resource.getText('KAF022_37'))
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
           <!-- ko let: {$bv: $data, text: nts.uk.resource.getText } -->
            <table id="fixed-table-b">
                <colgroup>
                    <col width="250px" />
                    <col width="600px" />
                </colgroup>
                <thead>
                    <tr>
                        <th class="ui-widget-header" id="b2" colspan="2" data-bind = "text: text('KAF022_119')"></th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td class="color-header" id="b3" data-bind = "text: text('KAF022_419')"></td>
                        <td>
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
            <!-- /ko -->`
});