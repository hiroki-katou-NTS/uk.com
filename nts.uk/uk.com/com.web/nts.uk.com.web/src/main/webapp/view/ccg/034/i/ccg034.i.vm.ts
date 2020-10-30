/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.com.view.ccg034.i {
  import CCG034D = nts.uk.com.view.ccg034.d;
  import getText = nts.uk.resource.getText;

  const MAXIMUM_IMAGE_COUNT = 4;
  const MAX_FILE_SIZE_B = 10 * 1024 * 1024;

  @bean()
  export class ScreenModel extends ko.ViewModel {
    partData: CCG034D.PartDataImageModel = null;
    //Choose file
    imageOption: ItemModel[] = [
      { code: 0, name: getText('CCG034_121') },
      { code: 1, name: getText('CCG034_123') }
    ];
    imageType: KnockoutObservable<number> = ko.observable(0);
    imageSrc: KnockoutObservable<string> = ko.observable('');
    imageList: ItemModel[] = [];
    // Upload file
    uploadedFileName: KnockoutObservable<string> = ko.observable('');
    fileSize: KnockoutObservable<number> = ko.observable(0);
    displayFileSize: KnockoutObservable<string> = ko.computed(() => {
      const vm = this;
      return nts.uk.text.format(getText("CCG034_125"), vm.fileSize()) + "KB";
    });
    fileId: KnockoutObservable<string> = ko.observable('');
    uploadSrc: KnockoutObservable<string> = ko.observable('');

    created(params: any) {
      const vm = this;
      vm.partData = params;
    }

    mounted() {
      const vm = this;
      // Binding part data
      vm.fileId(vm.partData.fileId);
      vm.imageSrc(vm.partData.fileName);
      vm.uploadedFileName(vm.partData.uploadedFileName);
      vm.fileId(vm.partData.fileId);
      vm.fileSize(vm.partData.uploadedFileSize ? vm.partData.uploadedFileSize : 0);
      vm.imageType(vm.partData.isFixed);

      if (vm.imageType() === 1) {
        nts.uk.request.ajax("/shr/infra/file/storage/infor/" + vm.fileId()).then((res: any) => vm.uploadFinished(res));
      }
      vm.createPopUp();
    }

    uploadFinished(data: any) {
      const vm = this;
      vm.fileId(data.id);
      vm.fileSize(Math.round(Number(data.originalSize) / 1024));
      var liveviewcontainer = $("#I2_2_2");
      liveviewcontainer.html("");
      liveviewcontainer.append($("<img class='pic-preview'/>").attr("src", (nts.uk.request as any).liveView(vm.fileId())));
    }

    createPopUp() {
      const vm = this;
      // Generate image list
      for (let i = 0; i < 40; i++) {
        vm.imageList.push({ code: i, name: "../resources/i/CCG034I_" + nts.uk.text.padLeft(String(i + 1), '0', 3) + ".png" });
      }
      // Adding images inside popup
      for (let i = 0; i < 40; i += MAXIMUM_IMAGE_COUNT) {
        let toAppend = "";
        for (let j = i; j < i + MAXIMUM_IMAGE_COUNT; j++) {
          toAppend += `<img id="I2_2_1_${j}" src="${vm.imageList[j].name}" class="pic-choose" data-bind="click: chooseImage" />`;
        }
        const template = `<div>
                            ${toAppend}
                          </div>`;
        $("#I2_4").append(template);
        // Rebind Knockout for the newly added div
        ko.applyBindings(vm, $("#I2_4 > div:last-child")[0]);
      }

      $("#I2_4").ntsPopup({
        trigger: "#I2_1_1",
        position: {
          my: "left top",
          at: "left bottom",
          of: "#I2_1_1"
        },
        showOnStart: false,
        dismissible: true
      });
    }

    chooseImage(data: any, event: any) {
      const vm = this;
      const itemId: string = event.currentTarget.id;
      const item = vm.imageList[Number(itemId.substr(7))];
      vm.imageSrc(item.name);
      $("#I2_4").ntsPopup("hide");
    }

    /**
    * Close dialog
    */
    public closeDialog() {
      const vm = this;
      vm.$window.close();
    }

    /**
     * Update part data and close dialog
     */
    public updatePartDataAndCloseDialog() {
      const vm = this;
      vm.$validate().then((valid: boolean) => {
        if (valid) {
          if (vm.fileSize() <= MAX_FILE_SIZE_B) {
            // Update part data
            const image = new Image();
            if (vm.imageType() === 0) {
              vm.partData.fileName = vm.imageSrc();
              image.src = vm.imageSrc();
            } else {
              vm.partData.fileId = vm.fileId();
              vm.partData.uploadedFileName = vm.uploadedFileName();
              vm.partData.uploadedFileSize = vm.fileSize();
              image.src = (nts.uk.request as any).liveView(vm.fileId());
            }
            vm.partData.ratio = image.naturalHeight / image.naturalWidth;
            vm.partData.isFixed = vm.imageType();

            // Return data
            vm.$window.close(vm.partData);
          } else vm.$dialog.error({ messageId: 'Msg_70', messageParams: [String(MAX_FILE_SIZE_B / (1024 * 1024))] });
        }
      });
    }
  }

  export interface ItemModel {
    code: number;
    name: string;
  }
}