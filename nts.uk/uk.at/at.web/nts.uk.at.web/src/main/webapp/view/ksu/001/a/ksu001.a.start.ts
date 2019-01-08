module nts.uk.at.view.ksu001.a {
    let __viewContext: any = window["__viewContext"] || {};

    __viewContext.ready(function() {
        __viewContext.viewModel = {
            viewO: new ksu001.o.viewmodel.ScreenModel(),
            viewQ: new ksu001.q.viewmodel.ScreenModel(),
            viewA: new ksu001.a.viewmodel.ScreenModel()
        };
        nts.uk.ui.block.grayout();
        __viewContext.viewModel.viewA.startKSU001().done(() => {
            __viewContext.bind(__viewContext.viewModel);
            __viewContext.viewModel.viewA.initCCG001().done(() => {
                nts.uk.ui.block.clear();
            });
        });

        initEvent();
    });

    function initEvent(): void {
        // Fire event.
        $("#multi-list").on('itemDeleted', (function(e: Event) {
            alert("Item is deleted in multi grid is " + e["detail"]["target"]);
        }));

        //popup 1
        $('#popup-area2').ntsPopup({
            position: {
                my: 'left top',
                at: 'left bottom+3',
                of: $('.create')
            }
        });

        $('.create').click(function() {
            $('#popup-area2').ntsPopup("toggle");
        });

        //popup 2
        $('#popup-area3').ntsPopup({
            position: {
                my: 'left top',
                at: 'left bottom+3',
                of: $('.check')
            }
        });

        $('.check').click(function() {
            $('#popup-area3').ntsPopup("toggle");
        });

        //popup 3
        $('#popup-area4').ntsPopup({
            position: {
                my: 'left top',
                at: 'left bottom+3',
                of: $('.vacation')
            }
        });

        $('.vacation').click(function() {
            $('#popup-area4').ntsPopup("toggle");
        });

        //popup 4
        $('#popup-area5').ntsPopup({
            position: {
                my: 'left top',
                at: 'left bottom+3',
                of: $('.setting')
            }
        });

        $('.setting').click(function() {
            $('#popup-area5').ntsPopup("toggle");
        });

        //popup A3-20
        $('#popup-area6').ntsPopup({
            position: {
                my: 'left top',
                at: 'left bottom+3',
                of: $('.setting-button')
            }
        });

        $('.setting-button').click(function() {
            $('#popup-area6').ntsPopup("toggle");
        });

        //popup A2-2
        $('#popup-area7').ntsPopup({
            position: {
                my: 'left top',
                at: 'left bottom+3',
                of: $('.done')
            }
        });

        $('.done').click(function() {
            $('#popup-area7').ntsPopup("toggle");
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

    }
}