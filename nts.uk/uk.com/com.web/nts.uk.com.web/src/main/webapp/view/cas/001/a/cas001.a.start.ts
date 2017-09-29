module nts.uk.com.view.cas001.a {
    __viewContext.ready(function() {
        __viewContext['screenModel'] = new viewmodel.ScreenModel();
        __viewContext['screenModel'].start().done(function() {
            __viewContext.bind(__viewContext['screenModel']);
            let helpButton = "<button id=\"A2_012\" data-bind=\"ntsHelpButton: {image: \'A2_012.png\', position: \'right top\', enable: true }\">?</button>";
            nts.uk.ui.ig.grid.header.getCell('A2_008', 'setting').append($(helpButton));
            ko.applyBindings(__viewContext['screenModel'], nts.uk.ui.ig.grid.header.getCell('A2_008', 'setting')[0]);


            //register click change all event
            $(function() {
                $('#anotherSelectedAll_auth, #seftSelectedAll_auth').on('click', '.nts-switch-button', function() {

                    __viewContext['screenModel'].changeAll($(this).parent().attr('id'));

                });

                $('#anotherSelectedAll_auth').on('keydown', function(event) {
                    if (event.keyCode === 37 || event.keyCode === 39) {
                        __viewContext['screenModel'].changeAll($(this).attr('id'));
                    }
                });

                $('#seftSelectedAll_auth').on('keydown', function(event) {
                    if (event.keyCode === 37 || event.keyCode === 39) {
                        __viewContext['screenModel'].changeAll($(this).attr('id'));
                    }
                });

            });
        });

    });
}