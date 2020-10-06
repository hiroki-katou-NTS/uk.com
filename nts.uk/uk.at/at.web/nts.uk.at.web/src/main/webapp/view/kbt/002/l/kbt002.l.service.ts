module nts.uk.at.view.kbt002.l.service {
  const paths = {
    indexReconstruction: "at/function/indexreconstruction"
  }

  export function indexResconstruction(execId: String): JQueryPromise<any>{
      return nts.uk.request.ajax("at",`${paths.indexReconstruction}/${execId}`);
  }
}