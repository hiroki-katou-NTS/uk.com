module nts.uk.at.view.ksu001.a {
    let __viewContext: any = window["__viewContext"] || {};
    let KEY = 'USER_INFOR';
    __viewContext.ready(function() {
        __viewContext.viewModel = {
            viewAB: new ksu001.ab.viewmodel.ScreenModel(),
            viewAC: new ksu001.ac.viewmodel.ScreenModel(),
            viewA: new ksu001.a.viewmodel.ScreenModel()
        };
        
        nts.uk.ui.block.grayout();
        __viewContext.viewModel.viewA.startPage().done(() => {
            __viewContext.bind(__viewContext.viewModel);
            
            uk.localStorage.getItem(KEY).ifPresent((data) => {
                let userInfor = JSON.parse(data);
                if (userInfor.disPlayFormat == 'shift') {
                    if (userInfor.shiftPalletUnit == 1) {
                        __viewContext.viewModel.viewAC.clickLinkButton(null, ko.observable(userInfor.shiftPalettePageNumberCom - 1));
                    } else if (userInfor.shiftPalletUnit == 2) {
                        __viewContext.viewModel.viewAC.clickLinkButton(null, ko.observable(userInfor.shiftPalettePageNumberOrg - 1));
                    }
                }
            });
            
            $(window).resize(function() {
                __viewContext.viewModel.viewA.setPositionButonDownAndHeightGrid();
            });

            nts.uk.ui.block.clear();
        });

        initEvent();
    });

    function initEvent(): void {
        // Fire event.
        $("#multi-list").on('itemDeleted', (function(e: Event) {
            alert("Item is deleted in multi grid is " + e["detail"]["target"]);
        }));

        //A1_10_1 click btn10
        $('#A1_10_1').ntsPopup({
            position: {
                my: 'left top',
                at: 'left bottom+3',
                of: $('#A1_10')
            }
        });

        $('#A1_10').click(function() {
            $('#A1_10_1').ntsPopup("toggle");
        });
        
        //A1_7_1 click btn7
        $('#A1_7_1').ntsPopup({
            position: {
                my: 'left top',
                at: 'left bottom+3',
                of: $('#A1_7')
            }
        });

        $('#A1_7').click(function() {
            $('#A1_7_1').ntsPopup("toggle");
        });
        

        // A1_12_1 click btn12
        $('#A1_12_1').ntsPopup({
            position: {
                my: 'right top',
                at: 'right bottom+3',
                of: $('#A1_12')
            }
        });

        $('#A1_12').click(function() {
            $('#A1_12_1').ntsPopup("toggle");
        });

        //click btnA4
        $('#A4_1').ntsPopup({
            position: {
                my: 'left top',
                at: 'left bottom+3',
                of: $('#A4')
            }
        });
        
        $('#A4').click(function() {
            $('#A4_1').ntsPopup("toggle");
        });
        
        
        //click btnA5
        $('#A5_1').ntsPopup({
            position: {
                my: 'left top',
                at: 'left bottom+3',
                of: $('#A5')
            }
        });

        $('#A5').click(function() {
            $('#A5_1').ntsPopup("toggle");
        });

        
        //popup setting grid
        $('#A16').ntsPopup({
            position: {
                my: 'left top',
                at: 'left bottom+3',
                of: $('.settingHeightGrid')
            }
        });

        $('.settingHeightGrid').click(function() {
            $('#A16').ntsPopup("toggle");
        });
    }
}