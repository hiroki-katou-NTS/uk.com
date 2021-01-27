module nts.uk.com.view.ccg034.share.model {

  export class HorizontalAlign {
    static LEFT = 0;
    static MIDDLE = 1;
    static RIGHT = 2;
  }

  export class VerticalAlign {
    static TOP = 0;
    static CENTER = 1;
    static BOTTOM = 2;
  }

  export function copyFile(fileId: string): string {
    const newFileId = nts.uk.util.randomId();
    nts.uk.request.ajax("/shr/infra/file/storage/infor/" + fileId)
      .done((fileInfo: any) => {
        fileInfo.id = newFileId;
        nts.uk.request.ajax("/ntscommons/arc/filegate/upload", fileInfo);
      });
    return newFileId;
  }
}
