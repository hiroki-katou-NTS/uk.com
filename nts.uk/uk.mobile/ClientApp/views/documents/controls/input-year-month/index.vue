<template>
  <div class="documentscontrolsinput-time">
    <h5>1. {{'sample' | i18n}}</h5>

    <h6>The Required Item</h6> 
    <nts-year-month
      v-model="yearMonth1"
      name="対象月１"
      v-bind:constraint= "{required: true}"
    />

    <h6>The Optional Item</h6>
    <nts-year-month
      v-model="yearMonth2"
      name="対象月２"
      v-bind:constraint= "{required: false}"
    />

    <h6>The Custom Item</h6>
    <nts-label v-bind:constraint="{required: true}">時間外労働時間</nts-label>
    <div class="row">
        <div class="col-3 note">対象月</div>
        <div class="col-7">
            <nts-year-month
            v-model="yearMonth3"
            v-bind:showTitle="false"
            name="時間外労働時間"
            v-bind:constraint= "{required: true}"
            />
        </div>
    </div>
    <markdown/>
  </div>
</template>