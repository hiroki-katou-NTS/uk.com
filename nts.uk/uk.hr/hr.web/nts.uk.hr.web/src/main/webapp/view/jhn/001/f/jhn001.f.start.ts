module jhn001.f {
    let __viewContext: any = window['__viewContext'] || {};
    __viewContext.ready(function() {
        __viewContext['viewModel'] = new vm.ViewModel();

        __viewContext['viewModel'].start().done(() => {
            init();
            $("span.filenamelabel.hyperlink").remove();
            __viewContext.bind(__viewContext['viewModel']);

            $("#button").click(function() {
                $("#custom-upload").ntsFileUpload({ stereoType: "avatarfile" }).done(function(res) {
                    nts.uk.ui.dialog.info("Upload successfully!");
                }).fail(function(err) {
                    nts.uk.ui.dialog.alertError(err);
                });
            });

            setTimeout(() => {
                $('.browser-button').attr("tabindex", 2);
                $(".link-button").attr("tabindex", 2);
                $(".delete-button").attr("tabindex", 2);
            }, 500);
        });
    });

}


ko.components.register('file-control', {
    viewModel: function(params: { items: any[] }) {
        const vm = {};

        _.extend(vm, params);

        setTimeout(() => {
            ko.computed(() => {
                const $grid = $('#grid2')
                    , items = ko.toJS(params.items);

                if ($grid.data('igGrid')) {
                    $grid.igGrid('destroy');
                }

                $grid.ntsGrid({
                    width: '850px',
                    height: '400px',
                    dataSource: items,
                    primaryKey: 'id',
                    virtualization: true,
                    virtualizationMode: 'continuous',
                    columns: [
                        { headerText: 'ID', key: 'id', dataType: 'string', width: '50px', hidden: true },
                        { headerText: nts.uk.resource.getText('JHN001_F2_3_1'), key: 'docName', dataType: 'string', width: '300px' },
                        { headerText: nts.uk.resource.getText('JHN001_F2_3_2'), key: 'fileName', dataType: 'string', width: '330px', template: '<a href="#" data-download="true" data-id="${id}">${name}</a>' },
                        { headerText: nts.uk.resource.getText('JHN001_F2_3_3'), key: 'open', dataType: 'string', width: '100px', template: '<div data-upload="true" data-id="${id}"></div>' },
                        { headerText: nts.uk.resource.getText('JHN001_F2_3_4'), key: 'delete', dataType: 'string', width: '120px', template: '<button data-delete="true" data-id="${id}">Delete</button>' }

                    ],
                    dataRendered: () => {
                        
                        $('#grid2 [data-download="true"]').on('click', (evt) => {
                            const button = evt.target;
                            console.log('download', $(button).attr('data-id'));
                        });

                        $('#grid2 [data-upload="true"]').each((__, element) => {
                            const id = $(element).attr('data-id');
                            
                            $(element).append(`<div data-bind="ntsFileUpload:{
                                    filename: ko.observable(''),
                                    accept: [],
                                    text: ko.observable(''),
                                    aslink: ko.observable(true),
                                    enable: ko.observable(true),
                                    uploadFinished: uploadFinished,
                                    stereoType: ko.observable('documentfile'),
                                    immediateUpload: true,
                                    maxSize: 10
                                }"></div>`);
                            
                            ko.cleanNode(element);
                            
                            ko.applyBindings({
                                id,
                                uploadFinished(file) {
                                    items.push(file);
                                    
                                    vm.items(items);
                                }
                            }, element);
                            
                        });

                        $('#grid2 [data-delete="true"]').on('click', (evt) => {
                            const button = evt.target;
                            console.log('delete', $(button).attr('data-id'));
                        });
                    }
                });
            });
        }, 25);

        return vm;
    },
    template: `<div id="grid2"></div>`
});

function init() { }

/*

function init() {
    const vm = __viewContext['viewModel']
         , $grid = $('#grid2');
    
    if($grid.data('igGrid')) {
        $grid.igGrid('destroy');
    }
    
    $grid.ntsGrid({
        width: '850px',
        height: '400px',
        dataSource: ko.toJS(vm.items),
        primaryKey: 'id',
        virtualization: true,
        virtualizationMode: 'continuous',
        columns: [
            { headerText: 'ID',                                     key: 'id', dataType: 'string', width: '50px', hidden: true },
            { headerText: nts.uk.resource.getText('JHN001_F2_3_1'), key: 'docName', dataType: 'string', width: '300px' },
            { headerText: nts.uk.resource.getText('JHN001_F2_3_2'), key: 'fileName', dataType: 'string', width: '330px', ntsControl: 'Link1' },
            { headerText: nts.uk.resource.getText('JHN001_F2_3_3'), key: 'open', dataType: 'string', width: '100px'   , template: "<div id ='file-upload' data-id='${id}' data-bind='ntsFileUpload:{ filename: filename, accept: accept,text: textId, aslink: asLink, enable: enable, uploadFinished: function(file) { uploadFinished(file, `${id}`) } , stereoType: stereoType,immediateUpload: true, maxSize: 10}'></div>" },
            { headerText: nts.uk.resource.getText('JHN001_F2_3_4'), key: 'delete', dataType: 'string', width: '120px' , template: "<button id ='delete-button' style='width: 77px' onclick='ButtonClick.call(this)' data-id='${id}'>" + nts.uk.resource.getText("CPS001_83") + "</button>" }

        ],
        features: [{ name: 'Sorting', type: 'local' }],
        ntsControls: [
            { name: 'Button', text: nts.uk.resource.getText('CPS001_83'), click: ButtonClick, controlType: 'Button' },
            { name: 'Link1', click: function() { LinkButtonClick.call(this); }, controlType: 'LinkLabel' } 
        ],
        dataRendered: () => {
            const vm = ko.dataFor($('#master-content').get(0));

            $('[data-bind^="ntsFileUpload"]').each((__, button) => {
                console.log(button);
                // ko.cleanNode(button);
                // ko.applyBindings(vm, button);
            });
        }
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
    $("#file-upload").ntsFileUpload("clear");
}
*/
