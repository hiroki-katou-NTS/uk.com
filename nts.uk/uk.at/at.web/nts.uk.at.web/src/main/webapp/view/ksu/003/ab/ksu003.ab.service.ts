module nts.uk.at.view.ksu003.ab {
	export module service {
        /**
         *  Service paths
         */
		let servicePath: any = {
			getTaskInfo : "screen/at/schedule/getTaskInfo",
			getTaskPallet: "screen/at/schedule/getTaskPallet",
		};

		/**
         * ①<<ScreenQuery>> 作業選択準備情報を取得する
         */
		export function getTaskInfo(command: any): JQueryPromise<any> {
			return nts.uk.request.ajax(servicePath.getTaskInfo, command);
		}

		/**
         * ①<<ScreenQuery>> 作業パレットを取得する
         */
		export function getTaskPallet(command: any): JQueryPromise<any> {
			return nts.uk.request.ajax(servicePath.getTaskPallet, command);
		}
	}
}