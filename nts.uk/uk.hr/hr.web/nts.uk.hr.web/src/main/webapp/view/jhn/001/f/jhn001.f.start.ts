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
                    width: '920px',
                    height: '400px',
                    dataSource: items,
                    primaryKey: 'id',
                    virtualization: true,
                    virtualizationMode: 'continuous',
                    columns: [
                        { headerText: 'ID', key: 'id', dataType: 'string', width: '50px', hidden: true },
                        { headerText: '',   key: 'urlFile', dataType: 'string', width: '50px', hidden: true },
                        { headerText: nts.uk.resource.getText('JHN001_F2_3_1'), key: 'docName', dataType: 'string', width: '300px' },
                        { headerText: nts.uk.resource.getText('JHN001_F2_3_2'), key: 'fileName', dataType: 'string', width: '400px', template: '<a href="${urlFile}" target="_blank" data-download="true" data-id="${id}" style="color: blue;" >${fileName}</a>'},
                        { headerText: nts.uk.resource.getText('JHN001_F2_3_3'), key: 'open', dataType: 'string', width: '100px', template: '<div data-upload="true" data-id="${id}" ></div>' },
                        { headerText: nts.uk.resource.getText('JHN001_F2_3_4'), key: 'delete', dataType: 'string', width: '120px', template: '<button data-delete="true" data-id="${id}"  style="width: 70px ; margin-left: 12px;" >削除</button>' }

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
                                    console.log(file);
                                    __viewContext['viewModel'].pushData(file, id);
                                }
                            }, element);
                            
                        });
                    
                        $('#grid2 [data-upload="true"] button').html(nts.uk.resource.getText('JHN001_F2_3_7'));
                        
                        $('#grid2 [data-delete="true"]').on('click', (evt) => {
                            const button = evt.target;
                            console.log('delete', $(button).attr('data-id'));
                            __viewContext['viewModel'].deleteItem($(button).attr('data-id'));
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

