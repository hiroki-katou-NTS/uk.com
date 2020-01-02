module jhn001.f {
    let __viewContext: any = window['__viewContext'] || {};
    __viewContext.ready(function() {
        __viewContext['viewModel'] = new vm.ViewModel();

        __viewContext['viewModel'].start().done(() => {
            init();
            __viewContext.bind(__viewContext['viewModel']);

            $("#button").click(function() {
                $("#custom-upload").ntsFileUpload({ stereoType: "avatarfile" }).done(function(res) {
                    nts.uk.ui.dialog.info("Upload successfully!");
                }).fail(function(err) {
                    nts.uk.ui.dialog.alertError(err);
                });
            });

            setTimeout(() => {
                $('.browser-button').focus();
                $('.browser-button').attr("tabindex", 2);
                $(".link-button").attr("tabindex", 2);
                $(".delete-button").attr("tabindex", 2);
            }, 500);
        });


        // focus to first input textbox
        $('input:first').focus();

    });

}


function init() {
    $("#grid2").ntsGrid({
        width: '850px',
        height: '400px',
        dataSource: __viewContext['viewModel'].items,
        primaryKey: 'id',
        virtualization: true,
        virtualizationMode: 'continuous',
        columns: [
            { headerText: 'ID', key: 'id', dataType: 'string', width: '50px', hidden: true },
            { headerText: nts.uk.resource.getText('JHN001_F2_3_1'), key: 'docName', dataType: 'string', width: '300px' },
            { headerText: nts.uk.resource.getText('JHN001_F2_3_2'), key: 'fileName', dataType: 'string', width: '330px', ntsControl: 'Link1' },
            { headerText: nts.uk.resource.getText('JHN001_F2_3_3'), key: 'open', dataType: 'string', width: '100px', unbound: true, template: "<div id='file-upload' data-id='${id}' data-bind='ntsFileUpload:{ filename: filename, accept: accept,text: textId, aslink: asLink, enable: enable, uploadFinished: function(file) { uploadFinished(file, `${id}`) } , stereoType: stereoType,immediateUpload: true, maxSize: 10}'></div>" },
            { headerText: nts.uk.resource.getText('JHN001_F2_3_4'), key: 'open', dataType: 'string', width: '120px', unbound: true, template: "<button class='delete-button' style='width: 77px' onclick='ButtonClick.call(this)' data-id='${id}'>" + nts.uk.resource.getText("CPS001_83") + "</button>" }

        ],
        features: [{ name: 'Sorting', type: 'local' }],
        ntsControls: [
            { name: 'Button', text: nts.uk.resource.getText('CPS001_83'), click: ButtonClick, controlType: 'Button' },
            { name: 'Link1', click: function() { LinkButtonClick.call(this); }, controlType: 'LinkLabel' }
        ]
    });

}

// xử lý click vào file
function LinkButtonClick() {
    var rowId: string = String($(this).closest("tr").data("id"));
    var rowItem = _.find(__viewContext['viewModel'].items, function(x: any) { return x.id == rowId; });
    nts.uk.request.ajax("/shr/infra/file/storage/infor/" + rowItem.fileId).done(function(res) {
        $('.filenamelabel').show();
        __viewContext['viewModel'].filename(res.originalName);
        nts.uk.request.specials.donwloadFile(rowItem.fileId);
    });
}
// xử lý xóa file
function ButtonClick() {
    var id = $(this).data("id");
    var rowItem = _.find(__viewContext['viewModel'].items, function(x: any) { return x.id == id; });
    __viewContext['viewModel'].deleteItem(rowItem);
    //__viewContext['viewModel'].filename('');
    $("#file-upload").ntsFileUpload("clear");
}

