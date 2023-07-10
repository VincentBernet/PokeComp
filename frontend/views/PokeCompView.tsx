import { JSXElementConstructor, Key, ReactElement, ReactFragment, ReactPortal, useEffect, useState } from "react";
import Pokemon from "Frontend/generated/com/example/application/Pokemon.js";
import { PokemonEndpoint, TeamEndpoint } from "Frontend/generated/endpoints.js";
import { TextField } from "@hilla/react-components/TextField.js";
import { Button } from "@hilla/react-components/Button.js";
import Team from "Frontend/generated/com/example/application/Team";

export const PokeCompView = () => {
    const [teamComposition, setTeamComposition] = useState<Team[]>([]);
    const [name, setName] = useState<string>('');

    useEffect(() => {
        PokemonEndpoint.findAll().then(setTeamComposition);
    }, []);

    const generateTeam = async () => {
        const saved = await TeamEndpoint.createTeam(name);
        if (saved) {
            setTeamComposition([...teamComposition, saved]);
            setName('');
        }
    }

    return (
        <div className="p-l">
            <h1>PokeComp!</h1>

            <div className="flex gap-s">
                <TextField value={name} onChange={e => setName(e.target.value)} />
                <Button theme="primary" onClick={generateTeam}>Generate Team</Button>
            </div>

            {teamComposition.map(team =>
                <div>
                    <span>Team : {team.id}</span>
                    {team.pokemonList?.map((pokemon: Pokemon | undefined) => (
                        <div key={pokemon?.id}>
                            <span>Pokemon :</span>
                            <span> Name =&gt; {pokemon?.name} | </span>
                            <span> Description =&gt; {pokemon?.description} |</span>
                            <span> Id =&gt; {pokemon?.id}</span>
                            <span> GameIndex =&gt; {pokemon?.gameIndex}</span>
                            <img src={"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/" + pokemon?.gameIndex + ".png"} />
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
}