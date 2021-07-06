import React, {useState, useEffect, useRef} from 'react';
import{BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import Request from '../helpers/request'; 

const MainContainer = () => {

    const [teams, setTeams] = useState([]);
    const [players, setPlayers] = useState([]);
    const [coaches, setCoaches] = useState([]);
    const [games, setGames] = useState([]);

    const requestAll = function(){
        const request = new Request();
        const teamPromise = request.get('/api/teams');
        const playerPromise = request.get('/api/players');
        const coachPromise = request.get('/api/coaches');
        const gamesPromise = request.get('api/games/create');

        Promise.all([teamPromise, playerPromise, coachPromise, gamesPromise])
        .then((data) => {
            setTeams(data[0]);
            setPlayers(data[1]);
            setCoaches(data[2]);
            setGames(data[3]);
        });
    }

    useEffect(() => {
        requestAll();
    }, []);

    return (
        <ul>
            <li>
                <p>teams.name</p>
            </li>
        </ul>
    )
}

export default MainContainer;