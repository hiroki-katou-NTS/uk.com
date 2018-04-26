module cps001.f {
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

            service.getCurrentEmpPermision().done((data: IPersonAuth) => {
                if (data && data.allowDocUpload != 1) {
                    $(".browser-button").attr('disabled', 'disabled');
                    $(".delete-button").attr('disabled', 'disabled');
                }
            });

            $('.browser-button').focus();
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
            { headerText: nts.uk.resource.getText('CPS001_81'), key: 'fileName', dataType: 'string', width: '750px', ntsControl: 'Link1' },
            { headerText: nts.uk.resource.getText('CPS001_83'), key: 'open', dataType: 'string', width: '50px', unbound: true, template: "<button class='delete-button' style='width: 77px' onclick='ButtonClick.call(this)' data-id='${id}'>" + nts.uk.resource.getText("CPS001_83") + "</button>" }

        ],
        features: [{ name: 'Sorting', type: 'local' }],
        ntsControls: [
            { name: 'Button', text: nts.uk.resource.getText('CPS001_83'), click: ButtonClick, controlType: 'Button' },
            { name: 'Link1', click: function() { LinkButtonClick.call(this); }, controlType: 'LinkLabel' }
        ]
    });

}

function LinkButtonClick() {
    var rowId: string = String($(this).closest("tr").data("id"));
    var rowItem = _.find(__viewContext['viewModel'].items, function(x: any) { return x.id == rowId; });
    nts.uk.request.ajax("/shr/infra/file/storage/infor/" + rowItem.fileId).done(function(res) {
        // set Text SizeFile
        let fileSize = ((res.originalSize) / 1024).toFixed(2);
        __viewContext['viewModel'].fileSize(nts.uk.resource.getText("CPS001_85", [fileSize]));
        __viewContext['viewModel'].filename(res.originalName);
        nts.uk.request.specials.donwloadFile(rowItem.fileId);

    });
}

function ButtonClick() {
    var id = $(this).data("id");
    var rowItem = _.find(__viewContext['viewModel'].items, function(x: any) { return x.id == id; });
    __viewContext['viewModel'].deleteItem(rowItem);
    __viewContext['viewModel'].fileSize('');
    __viewContext['viewModel'].filename('');

}

interface IPersonAuth {
    roleId: string;
    allowMapUpload: number;
    allowMapBrowse: number;
    allowDocRef: number;
    allowDocUpload: number;
    allowAvatarUpload: number;
    allowAvatarRef: number;
}

