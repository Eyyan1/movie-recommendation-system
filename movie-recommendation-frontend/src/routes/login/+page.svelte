<svelte:head>
	<title>Login</title>
</svelte:head>

<div class="mx-auto max-w-md">
	<section class="rounded-[1.75rem] border border-white/10 bg-white/5 p-8 shadow-xl shadow-slate-950/30">
		<p class="text-sm font-semibold uppercase tracking-[0.22em] text-sky-300">Authentication</p>
		<h1 class="mt-3 font-display text-4xl text-white">Sign in</h1>
		<p class="mt-3 text-sm leading-7 text-slate-400">
			This screen is ready for later API wiring to the Spring Boot backend login flow.
		</p>

		<form class="mt-8 space-y-4">
			<input class="w-full rounded-2xl border border-white/10 bg-slate-950/80 px-4 py-3 text-sm text-white placeholder:text-slate-500" placeholder="Username or email" />
			<input type="password" class="w-full rounded-2xl border border-white/10 bg-slate-950/80 px-4 py-3 text-sm text-white placeholder:text-slate-500" placeholder="Password" />
			<button type="button" class="w-full rounded-2xl bg-amber-300 px-5 py-3 text-sm font-semibold text-slate-950">Connect backend auth next</button>
		</form>
	</section>
</div>
