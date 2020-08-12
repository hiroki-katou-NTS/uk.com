<template>
  <div>
    <label class="col-12 mb-3"
      >{{ "KDPS01_66" | i18n }}
      {{ setting.error.lastDateError | date("YYYY/MM/DD（ddd）") }}
    </label>

    <label
      v-bind:style="{
        color: setting.error.messageColor
      }"
      class="col-12 mb-3"
      >{{ setting.error.messageContent }}
    </label>

    <div
      v-for="button in setting.buttons"
      v-bind:key="button.type"
      class="col-12 mb-2 px-0"
    >
      <button
        type="button"
        class=" col-12 btn btn-success btn-block btn-lg"
        v-click:500="() => goto(button)"
      >
        {{ button.dispName | i18n }}
      </button>
    </div>

    <button
      type="button"
      v-on:click="$close"
      class="col-12 btn btn-secondary btn-block mt-2"
    >
      {{ "KDPS01_65" | i18n }}
    </button>
  </div>
</template>
