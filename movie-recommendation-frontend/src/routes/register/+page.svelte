<svelte:head>
	<title>Register</title>
</svelte:head>

<div class="mx-auto max-w-md">
	<section class="rounded-[1.75rem] border border-white/10 bg-white/5 p-8 shadow-xl shadow-slate-950/30">
		<p class="text-sm font-semibold uppercase tracking-[0.22em] text-amber-300">New Account</p>
		<h1 class="mt-3 font-display text-4xl text-white">Create account</h1>
		<p class="mt-3 text-sm leading-7 text-slate-400">
			This form is intentionally frontend-only for now and is prepared to connect to the Spring Boot registration flow next.
		</p>

		<form class="mt-8 space-y-4">
			<input class="w-full rounded-2xl border border-white/10 bg-slate-950/80 px-4 py-3 text-sm text-white placeholder:text-slate-500" placeholder="Username" />
			<input class="w-full rounded-2xl border border-white/10 bg-slate-950/80 px-4 py-3 text-sm text-white placeholder:text-slate-500" placeholder="Email" />
			<input type="password" class="w-full rounded-2xl border border-white/10 bg-slate-950/80 px-4 py-3 text-sm text-white placeholder:text-slate-500" placeholder="Password" />
			<button type="button" class="w-full rounded-2xl bg-amber-300 px-5 py-3 text-sm font-semibold text-slate-950">Prepare account flow</button>
		</form>
	</section>
</div>
