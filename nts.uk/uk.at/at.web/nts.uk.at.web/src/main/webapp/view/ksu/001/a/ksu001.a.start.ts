module nts.uk.at.view.ksu001.a {
    let __viewContext: any = window["__viewContext"] || {};

    __viewContext.ready(function() {
        __viewContext.viewModel = {
            viewO: new ksu001.o.viewmodel.ScreenModel(),
            viewQ: new ksu001.q.viewmodel.ScreenModel(),
            viewA: new ksu001.a.viewmodel.ScreenModel()
        };
        let key = "USER_INFOR";
        let item = uk.localStorage.getItem(key);
        if (!item.isPresent()){
            let userInfor: any = {};
            userInfor.startDate = '';
            userInfor.endDate = '';
            userInfor.standardOrganization = 'standardOrganization';
            userInfor.listShiftMaster = [{a : '5', b : '6'},{a : '5', b : '6'}];
            userInfor.disPlayFormat = '';  
            userInfor.backgroundColor = ''; // 背景色
            userInfor.gridHeightSelection = '';
            userInfor.heightGridSetting = '';
            userInfor.shiftPalletUnit  = 1;
            userInfor.shiftPalletPositionNumber = 0;
            
            uk.localStorage.setItemAsJson(key, userInfor);
        } 
        
        nts.uk.ui.block.grayout();
        __viewContext.viewModel.viewA.startKSU001().done(() => {
            __viewContext.bind(__viewContext.viewModel);
            __viewContext.viewModel.viewA.getSettingDisplayWhenStart();
            
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

        //popup 3
        $('#popup-area4').ntsPopup({
            position: {
                my: 'left top',
                at: 'left bottom+3',
                of: $('.revision')
            }
        });

        $('.revision').click(function() {
            $('#popup-area4').ntsPopup("toggle");
        });

        //popup 4
        $('#popup-area5').ntsPopup({
            position: {
                my: 'right top',
                at: 'right bottom+3',
                of: $('.setmenu')
            }
        });

        $('.setmenu').click(function() {
            $('#popup-area5').ntsPopup("toggle");
        });

        //popup A3-20 popup-setting-grid
        $('#popup-area6').ntsPopup({
            position: {
                my: 'left top',
                at: 'left bottom+3',
                of: $('#A4')
            }
        });
        

        $('#A4').click(function() {
            $('#popup-area6').ntsPopup("toggle");
        });
        
        //popup A3-24
        $('#popup-area9').ntsPopup({
            position: {
                my: 'left top',
                at: 'left bottom+3',
                of: $('.color-button')
            }
        });

        $('.color-button').click(function() {
            $('#popup-area9').ntsPopup("toggle");
        });

        //popup setting grid
        $('#popup-setting-grid').ntsPopup({
            position: {
                my: 'left top',
                at: 'left bottom+3',
                of: $('.toSettingGrid')
            }
        });

        $('.toSettingGrid').click(function() {
            $('#popup-setting-grid').ntsPopup("toggle");
        });
    }
}