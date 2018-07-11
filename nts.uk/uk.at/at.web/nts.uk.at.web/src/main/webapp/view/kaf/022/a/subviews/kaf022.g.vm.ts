import text = nts.uk.resource.getText;

ko.components.register('kaf022-g', {
    viewModel: {
        createViewModel: function(params, componentInfo) {
            let vm = {
                // Datas
                itemListG16: ko.observableArray([
                    { code: 0, name: text('KAF022_221') },
                    { code: 1, name: text('KAF022_222') }
                ]),
                selectedIdG16: ko.observable(),
                itemListG18: ko.observableArray([
                    { code: 1, name: text('KAF022_75') },
                    { code: 0, name: text('KAF022_82') }
                ]),
                selectedIdG18: ko.observable(),
                selectedIdG20: ko.observable(),
                selectedIdG22: ko.observable(),
                itemListG23: ko.observableArray([
                    { code: 0, name: text('KAF022_173') },
                    { code: 1, name: text('KAF022_175') }
                ]),
                selectedIdG23: ko.observable(),
                itemListG24: ko.observableArray([
                    { code: 3, name: text('KAF022_198') },
                    { code: 2, name: text('KAF022_199') },
                    { code: 1, name: text('KAF022_200') },
                    { code: 0, name: text('KAF022_201') }
                ]),
                selectedIdG24: ko.observable(),
                itemListG25: ko.observableArray([
                    { code: 0, name: text('KAF022_226') },
                    { code: 1, name: text('KAF022_227') }
                ]),
                selectedIdG25: ko.observable(),
                selectedIdG26: ko.observable(),
                selectedIdG27: ko.observable(),
                selectedIdG28: ko.observable(),
                selectedIdG29: ko.observable(),
                // Behaviors
            };

            return vm;
        }
    },
    template: `
    <!-- ko let: { $gv: $data, text: nts.uk.resource.getText } -->
    <table id="fixed-table-g">
        <colgroup>
            <col width="250px" />
            <col width="600px" />
        </colgroup>
        <thead>
            <tr>
                <th class="ui-widget-header" id="g2" colspan="2" data-bind="text: text('KAF022_206')"></th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td class="color-header" id="g3" data-bind="text: text('KAF022_207')"></td>
                <td class="e11-radio_1">
                    <div tabindex="1" data-bind="ntsRadioBoxGroup: {
                            options: $gv.itemListG16,
                            optionsValue: 'code',
                            optionsText: 'name',
                            value: $gv.selectedIdG16,
                            enable: true
                        }">
                    </div>
                </td>
            </tr>
            <tr>
                <td class="color-header" id="g4" data-bind="text: text('KAF022_208')"></td>
                
            </tr>
            <tr>
                <td class="color-header" id="g5" data-bind="text: text('KAF022_209')"></td>
                <td class="g11-radio_4">
                    <div data-bind="text: text('KAF022_223')"></div>
                    <div tabindex="2" data-bind="ntsRadioBoxGroup: {
                            options: $gv.itemListG18,
                            optionsValue: 'code',
                            optionsText: 'name',
                            value: $gv.selectedIdG18,
                            enable: true
                        }">
                    </div>
                </td>
            </tr>
            <tr>
                <td class="color-header" id="g6" data-bind="text: text('KAF022_210')"></td>
            </tr>
            <tr>
                <td class="color-header" id="g7" data-bind="text: text('KAF022_211')"></td>
                <td class="g11-radio_4">
                    <div data-bind="text: text('KAF022_224')"></div>
                    <div tabindex="3" data-bind="ntsRadioBoxGroup: {
                            options: $gv.itemListG18,
                            optionsValue: 'code',
                            optionsText: 'name',
                            value: $gv.selectedIdG20,
                            enable: true
                        }">
                    </div>
                </td>
            </tr>
            <tr>
                <td class="color-header" id="g8" data-bind="text: text('KAF022_212')"></td>
                <td class="g11-radio_4">
                    <div data-bind="text: text('KAF022_225')"></div>
                    <div tabindex="4" data-bind="ntsRadioBoxGroup: {
                            options: $gv.itemListG18,
                            optionsValue: 'code',
                            optionsText: 'name',
                            value: $gv.selectedIdG22,
                            enable: true
                        }">
                    </div>
                </td>
            </tr>
            <tr>
                <td class="color-header" id="g9" data-bind="text: text('KAF022_213')"></td>
                <td class="g11-radio_5">
                    <div tabindex="5" data-bind="ntsRadioBoxGroup: {
                            options: $gv.itemListG23,
                            optionsValue: 'code',
                            optionsText: 'name',
                            value: $gv.selectedIdG23,
                            enable: true
                        }">
                    </div>
                </td>
            </tr>
            <tr>
                <td class="color-header" id="g10" data-bind="text: text('KAF022_214')"></td>
                <td >
                    <div id="g24" tabindex="6" data-bind="ntsRadioBoxGroup: {
                            options: $gv.itemListG24,
                            optionsValue: 'code',
                            optionsText: 'name',
                            value: $gv.selectedIdG24,
                            enable: true
                        }">
                    </div>
                </td>
            </tr>
            <tr>
                <td class="color-header" id="g11" data-bind="text: text('KAF022_215')"></td>
                <td class="g11-radio_4">
                    <div id="g25" tabindex="7" data-bind="ntsRadioBoxGroup: {
                            options: $gv.itemListG25,
                            optionsValue: 'code',
                            optionsText: 'name',
                            value: $gv.selectedIdG25,
                            enable: true
                        }">
                    </div>
                </td>
            </tr>
            <tr>
                <td class="color-header" id="g12" data-bind="text: text('KAF022_216')"></td>
                <td class="g11-radio_4">
                    <div tabindex="8" data-bind="ntsRadioBoxGroup: {
                            options: $gv.itemListG18,
                            optionsValue: 'code',
                            optionsText: 'name',
                            value: $gv.selectedIdG26,
                            enable: true
                        }">
                    </div>
                </td>
            </tr>
            <tr>
                <td class="color-header" id="g13" data-bind="text: text('KAF022_217')"></td>
                <td class="g11-radio_4">
                    <div tabindex="9" data-bind="ntsRadioBoxGroup: {
                            options: $gv.itemListG18,
                            optionsValue: 'code',
                            optionsText: 'name',
                            value: $gv.selectedIdG27,
                            enable: true
                        }">
                    </div>
                </td>
            </tr>
            <tr>
                <td class="color-header" id="g14" data-bind="text: text('KAF022_218')"></td>
                <td class="g11-radio_4">
                    <div tabindex="10" data-bind="ntsRadioBoxGroup: {
                            options: $gv.itemListG18,
                            optionsValue: 'code',
                            optionsText: 'name',
                            value: $gv.selectedIdG28,
                            enable: true
                        }">
                    </div>
                </td>
            </tr>
            <tr>
                <td class="color-header" id="g15" data-bind="text: text('KAF022_219')"></td>
                <td class="g11-radio_4">
                    <div tabindex="11" data-bind="ntsRadioBoxGroup: {
                            options: $gv.itemListG18,
                            optionsValue: 'code',
                            optionsText: 'name',
                            value: $gv.selectedIdG29,
                            enable: true
                        }">
                    </div>
                </td>
            </tr>
        </tbody>
    </table>
    <!-- /ko -->
    `
});