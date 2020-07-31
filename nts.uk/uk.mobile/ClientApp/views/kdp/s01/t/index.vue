<template>
  <div>
    <label class="col-12 mb-3">前回はお休みしています。申請しますか？</label>

    <div
      v-for="button in setting.buttons"
      v-bind:key="button.type"
      class="col-12 mb-2 px-0"
    >
      <button
        type="button"
        class=" col-12 btn btn-success btn-block btn-lg"
      >
        {{ button.displayText | i18n }}
      </button>
    </div>

    <button type="button" class="col-12 btn btn-secondary btn-block">
      {{ "KDPS01_65" | i18n }}
    </button>
  </div>
</template>
