<template>
  <div class="documentscontrolsinput-time">
    <h5>1. {{'sample' | i18n}}</h5>

    <nts-year-month
      v-model="yearMonth1"
      name="対象月１"
      :constraint= "{required: true}"
    />

    <nts-year-month
      v-model="yearMonth2"
      name="対象月２"
      :constraint= "{required: false}"
    />


    <nts-label v-bind:constraint="{required: true}">時間外労働時間</nts-label>
    <div class="row">
        <div class="col-3 note">対象月</div>
        <div class="col-7">
            <nts-year-month
            v-model="yearMonth2"
            :showTitle="false"
            name="時間外労働時間"
            :constraint= "{required: true}"
            />
        </div>
    </div>
    <markdown/>

    <br>
    <label>
      Tạo bởi:
      <b>Phạm Văn Dân</b>
    </label>
    <br>
  </div>
</template>